/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2017, Arnaud Roques
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
 *
 */
package net.sourceforge.plantuml.cucadiagram;

import java.util.StringTokenizer;

import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.HtmlColorSet;
import net.sourceforge.plantuml.graphic.color.ColorType;
import net.sourceforge.plantuml.graphic.color.Colors;

public abstract class WithLinkType {

	protected LinkType type;
	protected boolean hidden = false;

	private Colors colors = Colors.empty();

	public final HtmlColor getSpecificColor() {
		return colors.getColor(ColorType.LINE);
	}

	public final void setSpecificColor(HtmlColor specificColor) {
		colors = colors.add(ColorType.LINE, specificColor);
	}

	public void setColors(Colors colors) {
		this.colors = colors;
	}

	public final Colors getColors() {
		return colors;
	}

	final public void goDashed() {
		type = type.goDashed();
	}

	final public void goDotted() {
		type = type.goDotted();
	}

	final public void goThickness(double thickness) {
		type = type.goThickness(thickness);
	}

	final public void goHidden() {
		this.hidden = true;
	}

	public abstract void goNorank();

	final public void goBold() {
		type = type.goBold();
	}

	public void applyStyle(String arrowStyle) {
		if (arrowStyle == null) {
			return;
		}
		final StringTokenizer st = new StringTokenizer(arrowStyle, ",");
		while (st.hasMoreTokens()) {
			final String s = st.nextToken();
			if (s.equalsIgnoreCase("dashed")) {
				this.goDashed();
			} else if (s.equalsIgnoreCase("bold")) {
				this.goBold();
			} else if (s.equalsIgnoreCase("dotted")) {
				this.goDotted();
			} else if (s.equalsIgnoreCase("hidden")) {
				this.goHidden();
			} else if (s.equalsIgnoreCase("plain")) {
				// Do nothing
			} else if (s.equalsIgnoreCase("norank")) {
				this.goNorank();
			} else if (s.startsWith("thickness=")) {
				this.goThickness(Double.parseDouble(s.substring("thickness=".length())));
			} else {
				final HtmlColor tmp = HtmlColorSet.getInstance().getColorIfValid(s);
				setSpecificColor(tmp);
			}
		}
	}

	public LinkType getType() {
		return type;
	}

}
