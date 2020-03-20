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

import java.awt.geom.Dimension2D;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.command.Position;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.graphic.AbstractTextBlock;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.TextBlock;
import net.sourceforge.plantuml.graphic.UDrawable;
import net.sourceforge.plantuml.graphic.color.Colors;
import net.sourceforge.plantuml.timingdiagram.graphic.Histogram;
import net.sourceforge.plantuml.timingdiagram.graphic.IntricatedPoint;
import net.sourceforge.plantuml.timingdiagram.graphic.PlayerDrawing;
import net.sourceforge.plantuml.timingdiagram.graphic.PlayerFrame;
import net.sourceforge.plantuml.timingdiagram.graphic.PlayerFrame2;
import net.sourceforge.plantuml.timingdiagram.graphic.Ribbon;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UTranslate;

public final class PlayerRobustConcise extends Player {

	private final Set<ChangeState> changes = new TreeSet<ChangeState>();
	private final List<TimeConstraint> constraints = new ArrayList<TimeConstraint>();
	private final List<TimingNote> notes = new ArrayList<TimingNote>();
	private final Map<String, String> statesLabel = new LinkedHashMap<String, String>();
	private final TimingStyle type;

	private String initialState;
	private PlayerDrawing cached;
	private Colors initialColors;

	public PlayerRobustConcise(TimingStyle type, String full, ISkinParam skinParam, TimingRuler ruler) {
		super(full, skinParam, ruler);
		this.type = type;
	}

	private PlayerDrawing buildPlayerDrawing() {
		if (type == TimingStyle.CONCISE) {
			return new Ribbon(ruler, skinParam, notes);
		}
		if (type == TimingStyle.ROBUST) {
			return new Histogram(ruler, skinParam, statesLabel.values());
		}
		throw new IllegalStateException();
	}

	public final PlayerFrame getPlayerFrame() {
		return new PlayerFrame2(getTitle());
	}

	public final TextBlock getPart1() {
		return new AbstractTextBlock() {

			public void drawU(UGraphic ug) {
				ug = ug.apply(getTranslateForTimeDrawing(ug.getStringBounder()));
				getTimeDrawing().getPart1().drawU(ug);
			}

			public Dimension2D calculateDimension(StringBounder stringBounder) {
				return getTimeDrawing().getPart1().calculateDimension(stringBounder);
			}
		};
	}

	public UDrawable getPart2() {
		return new UDrawable() {
			public void drawU(UGraphic ug) {
				ug = ug.apply(getTranslateForTimeDrawing(ug.getStringBounder()));
				getTimeDrawing().getPart2().drawU(ug);
			}
		};
	}

	private UTranslate getTranslateForTimeDrawing(StringBounder stringBounder) {
		return UTranslate.dy(getTitleHeight(stringBounder));
	}

	public final double getFullHeight(StringBounder stringBounder) {
		return getTitleHeight(stringBounder) + getZoneHeight(stringBounder);
	}

	private double getTitleHeight(StringBounder stringBounder) {
		return getTitle().calculateDimension(stringBounder).getHeight() + 6;
	}

	private PlayerDrawing getTimeDrawing() {
		if (cached == null) {
			cached = computeTimeDrawing();
		}
		return cached;
	}

	private PlayerDrawing computeTimeDrawing() {
		final PlayerDrawing result = buildPlayerDrawing();
		result.setInitialState(initialState, initialColors);
		for (ChangeState change : changes) {
			result.addChange(change);
		}
		for (TimeConstraint constraint : constraints) {
			result.addConstraint(constraint);
		}
		return result;
	}

	private double getZoneHeight(StringBounder stringBounder) {
		return getTimeDrawing().getFullHeight(stringBounder);
	}

	public final void setState(TimeTick now, String comment, Colors color, String... states) {
		for (int i = 0; i < states.length; i++) {
			states[i] = decodeState(states[i]);
		}
		if (now == null) {
			this.initialState = states[0];
			this.initialColors = color;
		} else {
			this.changes.add(new ChangeState(now, comment, color, states));
		}

	}

	private String decodeState(String code) {
		final String label = statesLabel.get(code);
		if (label == null) {
			return code;
		}
		return label;
	}

	public final IntricatedPoint getTimeProjection(StringBounder stringBounder, TimeTick tick) {
		final IntricatedPoint point = getTimeDrawing().getTimeProjection(stringBounder, tick);
		if (point == null) {
			return null;
		}
		final UTranslate translation = getTranslateForTimeDrawing(stringBounder);
		return point.translated(translation);
	}

	public final void createConstraint(TimeTick tick1, TimeTick tick2, String message) {
		this.constraints.add(new TimeConstraint(tick1, tick2, message, skinParam));
	}

	public final void addNote(TimeTick now, Display note, Position position) {
		this.notes.add(new TimingNote(now, this, note, position, skinParam));
	}

	public final void defineState(String stateCode, String label) {
		statesLabel.put(stateCode, label);
	}

}
