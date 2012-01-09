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

import java.util.regex.Pattern;

/**
 * Store of Patterns
 */
public class SOP {
	public static Pattern fb2File;
	public static Pattern fb2zipFile;
	public static Pattern epubFile;
	public static Pattern opfFile;
	public static Pattern xmlEncoding;
	public static Pattern fb2FirstName;
	public static Pattern fb2MiddleName;
	public static Pattern fb2LastName;
	public static Pattern fb2Author;
	public static Pattern fb2Title;
	public static Pattern fb2genre;
	public static Pattern fb2Language;
	public static Pattern fb2Sequence;
	public static Pattern fb2SequenceContent;
	public static Pattern fb2Annotation;
	public static Pattern epubDescription;
	public static Pattern epubTitle;
	public static Pattern epubAuthor;
	public static Pattern epubLanguage;
	public static Pattern epubGenre;
//
//	
	static {
		fb2File = Pattern.compile("(?i).*fb2$");
		fb2zipFile = Pattern.compile("(?i).*fb2\\.zip$");
		epubFile = Pattern.compile("(?i).*epub$");
		opfFile = Pattern.compile("(?i).*opf$");
		xmlEncoding = Pattern.compile("(?i).*encoding=[\"'](.*?)[\"'].*");
		fb2FirstName = Pattern.compile("(?s)<first-name>(.*)</first-name>");
		fb2MiddleName = Pattern
				.compile("(?s)<middle-name>(.*)</middle-name>");
		fb2LastName = Pattern.compile("(?s)<last-name>(.*)</last-name>");
		fb2Author = Pattern.compile("(?s)<author>(.*?)</author>");
		fb2Title = Pattern.compile("(?s)<book-title>(.*?)</book-title>");
		fb2genre = Pattern.compile("(?s)<genre>(.*?)</genre>");
		fb2Language = Pattern.compile("(?s)<lang>(.*?)</lang>");
		fb2Sequence = Pattern.compile("(?s)<sequence(.*)>");
		fb2SequenceContent = Pattern.compile("\"(.*?)\"");

		fb2Annotation = Pattern
				.compile("(?s)<annotation>(.*?)</annotation>");
		epubDescription = Pattern
				.compile("(?s)<dc:description>(.*?)</dc:description>");
		epubTitle = Pattern
				.compile("(?s)<dc:title>(.*?)</dc:title>");
		epubAuthor = Pattern
				.compile("(?s)<dc:creator.*?>(.*?)</dc:creator>");
		epubLanguage = Pattern
				.compile("(?s)<dc:language.*?>(.*?)</dc:language>");
		epubGenre = Pattern
				.compile("(?s)<dc:subject.*?>(.*?)</dc:subject>");
	}


}
