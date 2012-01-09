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
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import ebook.EBook;
import ebook.Person;

/**
 * EpubInstantParser
 */
public class EpubInstantParser {
	private final int MAX_EPUBINFO_SIZE = 2048;
	private final int MAX_XMLINFO_SIZE = 80;
	private EBook eBook;
	private String source;

	EpubInstantParser(EBook eBook) throws IOException {
		this.eBook = eBook;
		this.source = this.createSource();
	}

	private String createSource() throws IOException {
		ZipEntry entry = null;
		ZipFile zipFile = new ZipFile(this.eBook.fileName);
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		while (entries.hasMoreElements()) {
			entry = entries.nextElement();
			if (entry.getName().matches("(?i).*\\.opf$"))
				break;
		}
		InputStream inputStream = zipFile.getInputStream(entry);
		byte[] buffer = readInputStream(inputStream);
		String encoding = getXmlEncoding(buffer);
		String preparedInput = new String(buffer, encoding);
		preparedInput = SOP.epubDescription.matcher(preparedInput)
				.replaceFirst("");
		return preparedInput;
	}

	private byte[] readInputStream(InputStream input) throws IOException {
		byte[] buffer = new byte[MAX_EPUBINFO_SIZE];
		int counter = 0;
		int amount = 0;
		int stopCounter = 0;
		boolean stop = false;
		while (!stop & (amount < MAX_EPUBINFO_SIZE) && (counter != -1)) {
			counter = input.read(buffer, amount, MAX_EPUBINFO_SIZE - amount);
			amount += counter;
			while (stopCounter < amount) {
				if (buffer[stopCounter] == '>')
					if (buffer[stopCounter - 1] == 'a')
						if (buffer[stopCounter - 10] == '<')
							if (buffer[stopCounter - 9] == '/') {
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

	public void parse() {
		Matcher matcher;
		matcher = SOP.epubTitle.matcher(this.source);
		if (matcher.find())
			this.eBook.title = matcher.group(1);
		matcher = SOP.epubAuthor.matcher(this.source);
		while (matcher.find()) {
			this.eBook.authors.add(new Person(matcher.group(1)));
		}
		matcher = SOP.epubLanguage.matcher(this.source);
		if (matcher.find())
			this.eBook.language = matcher.group(1);
		this.eBook.isOk = true;
		matcher = SOP.epubGenre.matcher(this.source);
		while (matcher.find())
			this.eBook.epubGenres.add(matcher.group(1));
		this.eBook.isOk = true;
	}
}
