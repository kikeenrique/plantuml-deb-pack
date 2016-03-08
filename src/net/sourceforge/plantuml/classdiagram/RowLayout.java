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
 * Revision $Revision: 14203 $
 *
 */
package net.sourceforge.plantuml.classdiagram;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.TextBlock;
import net.sourceforge.plantuml.graphic.UDrawable;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UTranslate;

public class RowLayout implements UDrawable {

	private final List<TextBlock> all = new ArrayList<TextBlock>();

	public void addLeaf(TextBlock entityImageClass) {
		this.all.add(entityImageClass);
	}

	public double getHeight(StringBounder stringBounder) {
		double y = 0;
		for (TextBlock leaf : all) {
			y = Math.max(y, leaf.calculateDimension(stringBounder).getHeight());
		}
		return y;
	}

	public void drawU(UGraphic ug) {
		double x = 0;
		for (TextBlock leaf : all) {
			leaf.drawU(ug.apply(new UTranslate(x, 0)));
			x += leaf.calculateDimension(ug.getStringBounder()).getWidth() + 20;
		}

	}

}
