/*
 * Copyright (C) 2011 Andrew Mochalov <avmae@mail.ru>
 * 
 *  This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA 
 */
package ebook.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.*;
import ebook.EBook;
import ebook.Person;

/**
 * FB2InstantParser
 */
class Fb2InstantParser {
	private final int MAX_FB2INFO_SIZE = 4096;
	private final int MAX_XMLINFO_SIZE = 80;
	private EBook eBook;
	private String source;

	Fb2InstantParser(EBook eBook, InputStream input) throws IOException {
		this.eBook = eBook;
		this.source = this.createSource(input);
	}

	private String createSource(InputStream stream) throws IOException, NullPointerException {
		byte[] buffer = readInputStream(stream);
		this.eBook.encoding = this.getXmlEncoding(buffer);
		String preparedInput = new String(buffer,this.eBook.encoding);
		preparedInput = SOP.fb2Annotation.matcher(preparedInput)
				.replaceFirst("");
		return preparedInput;
	}

	private byte[] readInputStream(InputStream input) throws IOException {
		byte[] buffer = new byte[MAX_FB2INFO_SIZE];
		int counter = 0;
		int amount = 0;
		int stopCounter = 0;
		boolean stop = false;
		while (!stop & (amount < MAX_FB2INFO_SIZE) && (counter != -1)) {
			counter = input.read(buffer, amount, MAX_FB2INFO_SIZE - amount);
			amount += counter;
			while (stopCounter < amount) {
				if (buffer[stopCounter] == '>')
					if (buffer[stopCounter - 1] == 'o')
						if (buffer[stopCounter - 12] == '<')
							if (buffer[stopCounter - 10] == 't') {
								stop = true;
								break;
							}
				stopCounter++;
			}
		}
		if (amount <= 0)
			throw new IOException("Epmty input stream");
		byte[] output = new byte[stopCounter];
		System.arraycopy(buffer, 0, output, 0, stopCounter);
		return output;
	}

	
	private String getXmlEncoding(byte[] input) throws IOException {
		String encoding = null;
		String xmlHeader = new String(input, 0, MAX_XMLINFO_SIZE, "ISO-8859-1");
		Matcher matcher = SOP.xmlEncoding.matcher(xmlHeader.toString());
		if (matcher.find())
			encoding = matcher.group(1);
		else
			throw new IOException("Unknown encoding");
		return encoding;
	}

	private Person extractPerson(String input) {
		Matcher matcher;
		Person person = new Person();
		matcher = SOP.fb2FirstName.matcher(input);
		if (matcher.find())
			person.firstName = matcher.group(1).trim();
		matcher = SOP.fb2MiddleName.matcher(input);
		if (matcher.find())
			person.middleName = matcher.group(1).trim();
		matcher = SOP.fb2LastName.matcher(input);
		if (matcher.find())
			person.lastName = matcher.group(1).trim();
		return person;
	}

	protected void parse() {
		Matcher matcher;
		matcher = SOP.fb2Author.matcher(source);
		while (matcher.find())
			this.eBook.authors.add(extractPerson(matcher.group(1)));
		matcher = SOP.fb2Title.matcher(source);
		if (matcher.find())
			this.eBook.title = matcher.group(1);
		matcher = SOP.fb2genre.matcher(source);
		while (matcher.find())
			this.eBook.fb2Genres.add(matcher.group(1));
		matcher = SOP.fb2Language.matcher(source);
		if (matcher.find())
			this.eBook.language = matcher.group(1);
		matcher = SOP.fb2Sequence.matcher(source);
		if (matcher.find()) {
			String sequence = matcher.group(1);
			matcher = SOP.fb2SequenceName.matcher(sequence);
			if (matcher.find())
				this.eBook.sequenceName = matcher.group(1);
			matcher = SOP.fb2SequenceNumber.matcher(sequence);
			if (matcher.find())
				this.eBook.sequenceNumber = matcher.group(1);
		}
		this.eBook.isOk = true;
	}
}
