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
 *
 */
package net.sourceforge.plantuml.project.draw;

import java.util.Map;

import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.graphic.HtmlColorSetSimple;
import net.sourceforge.plantuml.graphic.HtmlColorUtils;
import net.sourceforge.plantuml.graphic.TextBlock;
import net.sourceforge.plantuml.project.DayAsDate;
import net.sourceforge.plantuml.project.DayOfWeek;
import net.sourceforge.plantuml.project.GCalendar;
import net.sourceforge.plantuml.project.LoadPlanable;
import net.sourceforge.plantuml.project.core.Month;
import net.sourceforge.plantuml.project.core.Wink;
import net.sourceforge.plantuml.project.timescale.TimeScaleDaily;
import net.sourceforge.plantuml.ugraphic.UChangeBackColor;
import net.sourceforge.plantuml.ugraphic.UChangeColor;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.ULine;
import net.sourceforge.plantuml.ugraphic.URectangle;
import net.sourceforge.plantuml.ugraphic.UTranslate;

public class TimeHeaderDaily extends TimeHeader {

	private static final int Y_POS_WEEKDAY = 16;
	private static final int Y_POS_NUMDAY = 28;

	private double getTimeHeaderHeight() {
		return Y_POS_NUMDAY + 13;
	}

	private final HtmlColor veryLightGray = new HtmlColorSetSimple().getColorIfValid("#E0E8E8");

	private final GCalendar calendar;
	private final LoadPlanable defaultPlan;
	private final Map<DayAsDate, HtmlColor> colorDays;
	private final Map<DayAsDate, String> nameDays;

	public TimeHeaderDaily(GCalendar calendar, Wink min, Wink max, LoadPlanable defaultPlan,
			Map<DayAsDate, HtmlColor> colorDays, Map<DayAsDate, String> nameDays, DayAsDate printStart,
			DayAsDate printEnd) {
		super(min, max, new TimeScaleDaily(calendar, printStart));
		this.calendar = calendar;
		this.defaultPlan = defaultPlan;
		this.colorDays = colorDays;
		this.nameDays = nameDays;
	}

	@Override
	public void drawTimeHeader(final UGraphic ug, double totalHeight) {
		drawCalendar(ug, totalHeight);
		drawHline(ug, 0);
		drawHline(ug, getFullHeaderHeight());

	}

	private void drawCalendar(final UGraphic ug, double totalHeight) {
		Month lastMonth = null;
		double lastChangeMonth = -1;
		Wink wink = min;
		while (wink.compareTo(max) <= 0) {
			final DayAsDate day = calendar.toDayAsDate(wink);
			final DayOfWeek dayOfWeek = day.getDayOfWeek();
			final boolean isWorkingDay = defaultPlan.getLoadAt(wink) > 0;
			final String d1 = "" + day.getDayOfMonth();
			final TextBlock num = getTextBlock(d1, 10, false);
			final double x1 = getTimeScale().getStartingPosition(wink);
			final double x2 = getTimeScale().getEndingPosition(wink);
			double startingY = getFullHeaderHeight();
			if (wink.compareTo(max.increment()) < 0) {
				final TextBlock weekDay = getTextBlock(dayOfWeek.shortName(), 10, false);

				final URectangle rect = new URectangle(x2 - x1 - 1, totalHeight - getFullHeaderHeight());
				if (isWorkingDay) {
					final HtmlColor back = colorDays.get(day);
					if (back != null) {
						ug.apply(new UChangeColor(null)).apply(new UChangeBackColor(back))
								.apply(new UTranslate(x1 + 1, getFullHeaderHeight())).draw(rect);
					}
					printCentered(ug.apply(new UTranslate(0, Y_POS_WEEKDAY)), weekDay, x1, x2);
					printCentered(ug.apply(new UTranslate(0, Y_POS_NUMDAY)), num, x1, x2);
				} else {
					ug.apply(new UChangeColor(null)).apply(new UChangeBackColor(veryLightGray))
							.apply(new UTranslate(x1 + 1, getFullHeaderHeight())).draw(rect);
				}
				if (lastMonth != day.getMonth()) {
					startingY = 0;
					if (lastMonth != null) {
						printMonth(ug, lastMonth, day.getYear(), lastChangeMonth, x1);
					}
					lastChangeMonth = x1;
					lastMonth = day.getMonth();
				}
			}
			drawVbar(ug, x1, startingY, totalHeight);
			wink = wink.increment();
		}
		final DayAsDate day = calendar.toDayAsDate(max);
		final double x1 = getTimeScale().getStartingPosition(wink);
		drawVbar(ug, x1, Y_POS_WEEKDAY, totalHeight);
		if (x1 > lastChangeMonth) {
			printMonth(ug, lastMonth, day.getYear(), lastChangeMonth, x1);
		}

		printNamedDays(ug);

	}

	private void printMonth(UGraphic ug, Month lastMonth, int year, double start, double end) {
		final TextBlock tiny = getTextBlock(lastMonth.shortName(), 12, true);
		final TextBlock small = getTextBlock(lastMonth.niceName(), 12, true);
		final TextBlock big = getTextBlock(lastMonth.niceName() + " " + year, 12, true);
		printCentered(ug, start, end, tiny, small, big);
	}

	private void drawVbar(UGraphic ug, double x, double y1, double y2) {
		final ULine vbar = new ULine(0, y2 - y1);
		ug.apply(new UChangeColor(HtmlColorUtils.LIGHT_GRAY)).apply(new UTranslate(x, y1)).draw(vbar);
	}

	private void printNamedDays(final UGraphic ug) {
		if (nameDays.size() > 0) {
			String last = null;
			for (Wink wink = min; wink.compareTo(max.increment()) <= 0; wink = wink.increment()) {
				final DayAsDate tmpday = calendar.toDayAsDate(wink);
				final String name = nameDays.get(tmpday);
				if (name != null && name.equals(last) == false) {
					final double x1 = getTimeScale().getStartingPosition(wink);
					final double x2 = getTimeScale().getEndingPosition(wink);
					final TextBlock label = getTextBlock(name, 12, false);
					final double h = label.calculateDimension(ug.getStringBounder()).getHeight();
					double y1 = getTimeHeaderHeight();
					double y2 = getFullHeaderHeight();
					label.drawU(ug.apply(new UTranslate(x1, Y_POS_NUMDAY + 11)));
				}
				last = name;
			}
		}
	}

	@Override
	public double getFullHeaderHeight() {
		return getTimeHeaderHeight() + getHeaderNameDayHeight();
	}

	private double getHeaderNameDayHeight() {
		if (nameDays.size() > 0) {
			return 16;
		}
		return 0;
	}

}
