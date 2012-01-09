/*
 * Copyright (C) 2011 Andrew Mochalov <avmae@mail.ru>
 * 
 *    This library is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU Lesser General Public
 *   License as published by the Free Software Foundation; either
 *   version 2.1 of the License, or (at your option) any later version.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *   Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the Free Software
 *   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA 
 */
package ebook;

import java.util.ArrayList;
import java.util.List;

/**
 * EBook
 */
public class EBook {
	public boolean isOk;
	public String fileName;
	public EBookFormat format;
	public ArrayList<Person> authors;
	public String title;
	public List<String> fb2Genres;
	public List<String> epubGenres;
	public String language;
	public String srcLanguage;
	public ArrayList<Person> translators;
	public String sequenceName;
	public String sequenceNumber;
	public String encoding;
	public String annotation;
	public byte[] cover;

	public EBook() {
		this.authors = new ArrayList<Person>(3);
		this.fb2Genres = new ArrayList<String>(2);
		this.epubGenres = new ArrayList<String>(2);
		this.translators = new ArrayList<Person>(2);
		this.isOk = false;
	}
}
