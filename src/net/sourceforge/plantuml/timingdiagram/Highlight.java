/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2020, Arnaud Roques
 *
 * Project Info:  http://plantuml.com
 * 
 * If you like this project or if you find it useful, you can support us at:
 * 
 * http://plantuml.com/patreon (only 1$ per month!)
 * http://plantuml.com/paypal
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
 *
 * Original Author:  Arnaud Roques
 *
 */
package net.sourceforge.plantuml.timingdiagram;

import net.sourceforge.plantuml.FontParam;
import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.graphic.FontConfiguration;
import net.sourceforge.plantuml.graphic.HorizontalAlignment;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.HtmlColorUtils;
import net.sourceforge.plantuml.graphic.TextBlock;
import net.sourceforge.plantuml.graphic.color.ColorType;
import net.sourceforge.plantuml.graphic.color.Colors;
import net.sourceforge.plantuml.ugraphic.UChangeBackColor;
import net.sourceforge.plantuml.ugraphic.UChangeColor;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.ULine;
import net.sourceforge.plantuml.ugraphic.URectangle;
import net.sourceforge.plantuml.ugraphic.UStroke;
import net.sourceforge.plantuml.ugraphic.UTranslate;

public class Highlight {

	private final TimeTick tickFrom;
	private final TimeTick tickTo;
	private final Display caption;
	private final Colors colors;

	public Highlight(TimeTick tickFrom, TimeTick tickTo, Display caption, Colors colors) {
		this.tickFrom = tickFrom;
		this.tickTo = tickTo;
		this.caption = caption;
		this.colors = colors;
	}

	private HtmlColor getBackColor() {
		final HtmlColor result = colors.getColor(ColorType.BACK);
		if (result == null) {
			return HtmlColorUtils.COL_A9DCDF;
		}
		return result;
	}

	private HtmlColor getLineColor() {
		final HtmlColor result = colors.getColor(ColorType.LINE);
		if (result == null) {
			return HtmlColorUtils.BLACK;
		}
		return result;
	}

	public final TimeTick getTickFrom() {
		return tickFrom;
	}

	public final TimeTick getTickTo() {
		return tickTo;
	}

	public final Display getCaption() {
		return caption;
	}

	public TextBlock getCaption(ISkinParam skinParam) {
		final FontConfiguration fc = new FontConfiguration(skinParam, FontParam.TIMING, null);
		return caption.create(fc, HorizontalAlignment.LEFT, skinParam);
	}

	public void drawHighlightsBack(UGraphic ug, TimingRuler ruler, double height) {
		ug = ug.apply(new UChangeColor(null)).apply(new UChangeBackColor(getBackColor()));
		final double start = ruler.getPosInPixel(this.getTickFrom());
		final double end = ruler.getPosInPixel(this.getTickTo());
		final URectangle rect = new URectangle(end - start, height);
		ug.apply(new UTranslate(start, 0)).draw(rect);
	}

	public void drawHighlightsLines(UGraphic ug, TimingRuler ruler, double height) {
		ug = ug.apply(new UStroke(4, 4, 2));
		ug = ug.apply(new UChangeColor(getLineColor()));
		final ULine line = new ULine(0, height);
		final double start = ruler.getPosInPixel(this.getTickFrom());
		final double end = ruler.getPosInPixel(this.getTickTo());
		ug.apply(new UTranslate(start, 0)).draw(line);
		ug.apply(new UTranslate(end, 0)).draw(line);
	}

}
