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
 * Revision $Revision: 8475 $
 *
 */
package net.sourceforge.plantuml.activitydiagram3.ftile;

import java.awt.geom.Dimension2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.plantuml.Direction;
import net.sourceforge.plantuml.graphic.HtmlColorAndStyle;
import net.sourceforge.plantuml.graphic.Rainbow;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.TextBlock;
import net.sourceforge.plantuml.graphic.TextBlockUtils;
import net.sourceforge.plantuml.ugraphic.CompressionTransform;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UPolygon;
import net.sourceforge.plantuml.ugraphic.UShape;
import net.sourceforge.plantuml.ugraphic.UTranslate;

public class Snake implements UShape {

	private final Worm worm = new Worm();
	private final UPolygon endDecoration;
	private final Rainbow color;
	private TextBlock textBlock;
	private boolean mergeable = true;
	private Direction emphasizeDirection;

	public Snake transformX(CompressionTransform compressionTransform) {
		final Snake result = new Snake(color, endDecoration);
		result.textBlock = this.textBlock;
		result.mergeable = this.mergeable;
		result.emphasizeDirection = this.emphasizeDirection;
		for (Point2D.Double pt : worm) {
			final double x = compressionTransform.transform(pt.x);
			final double y = pt.y;
			result.addPoint(x, y);
		}
		return result;
	}

	public Snake(Rainbow color, UPolygon endDecoration) {
		if (color == null) {
			throw new IllegalArgumentException();
		}
		if (color.size() == 0) {
			throw new IllegalArgumentException();
		}
		this.endDecoration = endDecoration;
		this.color = color;
	}

	public Snake(Rainbow color) {
		this(color, null);
	}

	public void setLabel(TextBlock label) {
		this.textBlock = label;
	}

	public Snake move(double dx, double dy) {
		final Snake result = new Snake(color, endDecoration);
		for (Point2D pt : worm) {
			result.addPoint(pt.getX() + dx, pt.getY() + dy);
		}
		result.textBlock = this.textBlock;
		result.mergeable = this.mergeable;
		result.emphasizeDirection = this.emphasizeDirection;
		return result;
	}

	public Snake translate(UTranslate translate) {
		return move(translate.getDx(), translate.getDy());
	}

	@Override
	public String toString() {
		return worm.toString();
	}

	public void addPoint(double x, double y) {
		worm.addPoint(x, y);
	}

	public void addPoint(Point2D p) {
		addPoint(p.getX(), p.getY());
	}

	public void drawInternal(UGraphic ug) {
		if (color.size() > 1) {
			drawRainbow(ug);
		} else {
			worm.drawInternalOneColor(ug, color.getColors().get(0), 1.5, emphasizeDirection, endDecoration);
			drawInternalLabel(ug);
		}

	}

	private void drawRainbow(UGraphic ug) {
		final List<HtmlColorAndStyle> colors = color.getColors();
		final int colorArrowSeparationSpace = color.getColorArrowSeparationSpace();
		final double move = 2 + colorArrowSeparationSpace;
		final WormMutation mutation = WormMutation.create(worm, move);
		ug = ug.apply(mutation.getGlobalTranslate(colors.size()));
		Worm current = worm;
		for (int i = 0; i < colors.size(); i++) {
			double stroke = 1.5;
			if (colorArrowSeparationSpace == 0) {
				stroke = i == colors.size() - 1 ? 2.0 : 3.0;
			}
			current.drawInternalOneColor(ug, colors.get(i), stroke, emphasizeDirection, endDecoration);
			current = mutation.mute(current);
		}
		final UTranslate textTranslate = mutation.getTextTranslate(colors.size());
		drawInternalLabel(ug.apply(textTranslate));
	}

	// private void drawRainbowOld(UGraphic ug) {
	// final HtmlColorRainbow rainbow = (HtmlColorRainbow) color;
	// final List<HtmlColor> colors = rainbow.getColors();
	// final int colorArrowSeparationSpace = rainbow.getColorArrowSeparationSpace();
	// final double move = 2 + colorArrowSeparationSpace;
	// ug = ug.apply(new UTranslate(-move * (colors.size() - 1) / 2.0, 0));
	// double dx = -move;
	// for (int i = 0; i < colors.size(); i++) {
	// dx += move;
	// double stroke = 1.5;
	// if (colorArrowSeparationSpace == 0) {
	// stroke = i == colors.size() - 1 ? 2.0 : 3.0;
	// }
	// worm.drawInternalOneColor(ug.apply(new UTranslate(dx, 0)), colors.get(i), stroke, emphasizeDirection,
	// endDecoration);
	// }
	// drawInternalLabel(ug.apply(new UTranslate(dx, 0)));
	// }

	private void drawInternalLabel(UGraphic ug) {
		if (textBlock != null) {
			final Point2D position = getTextBlockPosition(ug.getStringBounder());
			// final double max = getMaxX(ug.getStringBounder());
			// ug.apply(new UChangeBackColor(HtmlColorUtils.LIGHT_GRAY))
			// .apply(new UTranslate(position.getX(), position.getY()))
			// .draw(new URectangle(textBlock.calculateDimension(ug.getStringBounder())));
			// ug.apply(new UChangeBackColor(HtmlColorUtils.RED)).apply(new UTranslate(0, position.getY() + 10))
			// .draw(new URectangle(max, 10));
			textBlock.drawU(ug.apply(new UTranslate(position)));
		}
	}

	public double getMaxX(StringBounder stringBounder) {
		double result = -Double.MAX_VALUE;
		for (Point2D pt : worm) {
			result = Math.max(result, pt.getX());
		}
		if (textBlock != null) {
			final Point2D position = getTextBlockPosition(stringBounder);
			final Dimension2D dim = textBlock.calculateDimension(stringBounder);
			result = Math.max(result, position.getX() + dim.getWidth());
		}
		return result;
	}

	private Point2D getTextBlockPosition(StringBounder stringBounder) {
		final Point2D pt1 = worm.get(0);
		final Point2D pt2 = worm.get(1);
		final Dimension2D dim = textBlock.calculateDimension(stringBounder);
		final double y = (pt1.getY() + pt2.getY()) / 2 - dim.getHeight() / 2;
		return new Point2D.Double(Math.max(pt1.getX(), pt2.getX()) + 4, y);
	}

	public List<Line2D> getHorizontalLines() {
		final List<Line2D> result = new ArrayList<Line2D>();
		for (int i = 0; i < worm.size() - 1; i++) {
			final Point2D pt1 = worm.get(i);
			final Point2D pt2 = worm.get(i + 1);
			if (pt1.getY() == pt2.getY()) {
				final Line2D line = new Line2D.Double(pt1, pt2);
				result.add(line);
			}
		}
		return result;

	}

	private Point2D getFirst() {
		return worm.get(0);
	}

	public Point2D getLast() {
		return worm.get(worm.size() - 1);
	}

	static boolean same(Point2D pt1, Point2D pt2) {
		return pt1.distance(pt2) < 0.001;
	}

	public Snake merge(Snake other) {
		if (mergeable == false || other.mergeable == false) {
			return null;
		}
		if (TextBlockUtils.isEmpty(other.textBlock) == false) {
			return null;
			// System.err.println("merge other.textBlock="+other.textBlock+" "+other.textBlock.calculateDimension(TextBlockUtils.getDummyStringBounder()));
		}
		// if (other.textBlock != null) {
		// return null;
		// }
		if (same(this.getLast(), other.getFirst())) {
			final UPolygon oneOf = endDecoration == null ? other.endDecoration : endDecoration;
			final Snake result = new Snake(color, oneOf);
			result.emphasizeDirection = emphasizeDirection == null ? other.emphasizeDirection : emphasizeDirection;
			result.worm.addAll(this.worm.merge(other.worm));
			return result;
		}
		if (same(this.getFirst(), other.getLast())) {
			return other.merge(this);
		}
		return null;
	}

	public void goUnmergeable() {
		this.mergeable = false;
	}

	public void emphasizeDirection(Direction direction) {
		this.emphasizeDirection = direction;
	}

}
