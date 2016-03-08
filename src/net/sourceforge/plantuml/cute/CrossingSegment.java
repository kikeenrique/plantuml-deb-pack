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
 * Revision $Revision: 4041 $
 *
 */
package net.sourceforge.plantuml.cute;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.plantuml.geom.LineSegmentDouble;
import net.sourceforge.plantuml.ugraphic.UTranslate;

public class CrossingSegment {

	private final Balloon balloon;
	private final LineSegmentDouble segment;

	public CrossingSegment(Balloon balloon, LineSegmentDouble segment) {
		this.balloon = balloon;
		this.segment = segment;
	}

	public List<Point2D> intersection() {
		final List<Point2D> result = new ArrayList<Point2D>();

		final UTranslate tr = new UTranslate(balloon.getCenter());
		final UTranslate trInverse = tr.reverse();

		final CrossingSimple simple = new CrossingSimple(balloon.getRadius(),
				new InfiniteLine(segment).translate(trInverse));
		for (Point2D pt : simple.intersection()) {
			pt = tr.getTranslated(pt);
			if (segment.isPointOnSegment(pt)) {
				result.add(pt);
			}
		}

		return result;
	}

}
