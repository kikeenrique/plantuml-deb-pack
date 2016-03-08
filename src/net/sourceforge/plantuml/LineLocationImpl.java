/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2017, Arnaud Roques
 *
 * Project Info:  http://plantuml.com
 * 
 * This file is part of PlantUML.
 *
 * PlantUML is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PlantUML distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public
 * License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * Original Author:  Arnaud Roques
 *
 * Revision $Revision: 3824 $
 *
 */
package net.sourceforge.plantuml;

public class LineLocationImpl implements LineLocation {

	private final String desc;
	private final int position;
	private final LineLocation parent;

	@Override
	public String toString() {
		if (desc == null) {
			return "[?] : " + position;
		}
		return desc + " : " + position;
	}

	public LineLocationImpl(String desc, LineLocation parent) {
		this(desc, parent, -1);
	}

	private LineLocationImpl(String desc, LineLocation parent, int position) {
		this.parent = parent;
		this.desc = desc;
		this.position = position;
	}

	public LineLocationImpl oneLineRead() {
		return new LineLocationImpl(desc, parent, position + 1);
	}

	public static LineLocation fromLine(CharSequence cs) {
		if (cs instanceof CharSequence2) {
			return ((CharSequence2) cs).getLocation();
		}
		return null;
	}

	public int getPosition() {
		return position;
	}

	public String getDescription() {
		return desc;
	}

	public LineLocation getParent() {
		return parent;
	}

}
