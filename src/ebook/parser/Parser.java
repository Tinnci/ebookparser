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

import ebook.EBook;

/**
 * Parser
 */
abstract public class Parser {
	protected EBook eBook;
	protected boolean isExtractCover;

	public EBook parse(String fileName) {
		return this.parse(fileName, false);
	}
	
	public EBook parse(String fileName, boolean extractCover) {
		this.isExtractCover = extractCover;
		this.eBook = new EBook();
		this.eBook.fileName = fileName;
		this.eBook.isOk = false;
		this.parseFile();
		return this.eBook;
	}
	
	
	abstract protected void parseFile();

	public EBook getEBoook() {
		return this.eBook;
	}
}
