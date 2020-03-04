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
package net.sourceforge.plantuml.donors;

import java.awt.geom.Dimension2D;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import net.sourceforge.plantuml.AbstractPSystem;
import net.sourceforge.plantuml.BackSlash;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.code.AsciiEncoder;
import net.sourceforge.plantuml.code.CompressionBrotli;
import net.sourceforge.plantuml.code.StringCompressorNone;
import net.sourceforge.plantuml.code.Transcoder;
import net.sourceforge.plantuml.code.TranscoderImpl;
import net.sourceforge.plantuml.core.DiagramDescription;
import net.sourceforge.plantuml.core.ImageData;
import net.sourceforge.plantuml.graphic.GraphicStrings;
import net.sourceforge.plantuml.graphic.HtmlColorUtils;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.TextBlock;
import net.sourceforge.plantuml.graphic.UDrawable;
import net.sourceforge.plantuml.svek.TextBlockBackcolored;
import net.sourceforge.plantuml.ugraphic.ColorMapperIdentity;
import net.sourceforge.plantuml.ugraphic.ImageBuilder;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UImage;
import net.sourceforge.plantuml.ugraphic.UTranslate;
import net.sourceforge.plantuml.version.PSystemVersion;

public class PSystemDonors extends AbstractPSystem {

	private static final int COLS = 6;
	private static final int FREE_LINES = 6;

	public static final String DONORS = "6taA0AmEU9ELAujmujswMQFg3ojH8OBbUK9n65Z4qzs0-0FSEptkDz9VH5UwzqqpeYr_pV7pZA4O27ey"
			+ "VrqDs_nl3nxxgV8W-Ev2KDA92uXiAx7-a7f_TK3C5DrlcT3URXqNwk24ckSZ-BYouVtDS9vl6X-zz0nO"
			+ "GjLFqNtj_qrTPLrULOLPkblL-UWAbR5LkrbpkljHu8ues7Seq23Kc4vTN8T3LOUvTgAlJemGOEAT4tpR"
			+ "jPWLenuY9LKtjY0b1YDqjqdhb-GN6RN_g1VGfhrSKo9dGddC4ecksqg7znrqFolvvOW7Nx19O06L7E4J"
			+ "5Brjn934UT3GEPlHsCAHBKy2C6IWZNtuHr4SAPwIcMreaIIaG4dAjt40CYfrQn6pvSs0yfxHObYJXCxt"
			+ "Xt3VE4och3feU0Wi4Yof3Qefo9lG4pxLLrqoEET3KSLVVflairaCD2XR0kJDZBw5HvD4-Py-aFdyRbBR"
			+ "FAMnrNNC_puSYg8fiuRVz3PgEO9xVrb8mU920SeHwAK-KKb-Vyg-iH23S6sb5z8njrr9emYNR4YhM9UV"
			+ "Oearwz-wnCQcnRBSMBIF_nTyKpBG4YkHPEXjMlbaxNoXf6bglB_arT3vRc9Dtapl6Ar7wPpc_tV3Iise"
			+ "X85ETp7AT4EWs8o6QgXPc0XC2zqS2XCW3w3sDBdbJwilyMkV8FEc4ed8RRZqWB_-JAvouS9KhEds9cO0"
			+ "auUKUf8aBOXGrY7eIQ1K8sKH-XacBTEMZedxf0qUfysCWH_LCSL9-6aXS7N3l9etMxkspp5RS8FlPJvr"
			+ "7zAuYkTqS-zv5wdv6mpeBIte4QanejZtial2LGCbihXB7XhYkuSTN3nqtu8pMRI16MsEGsP2vLeoQkaj"
			+ "HXcQTBubapNjrLPK6X1U7EFWZenEy7WZUQGMbqZYPZ2325i0qlhY4xiauSw4gbjkTZRN5gODNxq8BS7l"
			+ "Kfd0KeKNdYxiwEv-SKPZ5DadvhqoUsayfXGV2wYIKmLLQQFqcLBaQMiP6JBMe6Uz3R3BgMuB-3O_rR1E"
			+ "ABojejCM6tIRGeQhX6yqYYTg5UqQkDYaC82lUNaAdZRMGSPtUbb7722RIq885Lfp-0UdfR7w22rblKN_"
			+ "jstRoyhvy-zrP0MdddZcLGNG8qZpVpzble15h-jx6_fMvLCUs06pnP0UR2rM7b1w9v3KO1-biugwDCs8"
			+ "htX7kKLjqSgF3vN_wNIFM4m7tx3yefeYdxx4QRQzD9nAqdtV5ukJ73R-AQhCokIgrAg94Tt4Te_YF8sT"
			+ "Podkkde54lIEImNkdhhUJjDyIf_p1QM7uOeXFn-H8PlghWMra9RZjC0sSTRtPhOXcUf_IGy-2RLrdDN3"
			+ "qC7sRt8NQFRjJMxS40K6HVo1WNtsc9CJ5CSR0kt6r1rfkPYoSz4ztIuMhB7Vz8_8ieG_B6kLYJ3NowrO" + "B0lnh6sq0m00";

	/*
	 * Special thanks to our sponsors and donors:
	 * 
	 * - Noam Tamim
	 */

	@Override
	final protected ImageData exportDiagramNow(OutputStream os, int num, FileFormatOption fileFormat, long seed)
			throws IOException {
		final UDrawable result = getGraphicStrings();
		final ImageBuilder imageBuilder = new ImageBuilder(new ColorMapperIdentity(), 1.0, HtmlColorUtils.WHITE,
				getMetadata(), null, 0, 0, null, false);
		imageBuilder.setUDrawable(result);
		return imageBuilder.writeImageTOBEMOVED(fileFormat, seed, os);
	}

	private UDrawable getGraphicStrings() throws IOException {
		final List<TextBlock> cols = getCols(getDonors(), COLS, FREE_LINES);
		return new UDrawable() {
			public void drawU(UGraphic ug) {
				final TextBlockBackcolored header = GraphicStrings
						.createBlackOnWhite(Arrays.asList("<b>Special thanks to our sponsors and donors !"));
				header.drawU(ug);
				final StringBounder stringBounder = ug.getStringBounder();
				ug = ug.apply(new UTranslate(0, header.calculateDimension(stringBounder).getHeight()));
				double x = 0;
				double lastX = 0;
				double y = 0;
				for (TextBlock tb : cols) {
					final Dimension2D dim = tb.calculateDimension(stringBounder);
					tb.drawU(ug.apply(new UTranslate(x, 0)));
					lastX = x;
					x += dim.getWidth() + 10;
					y = Math.max(y, dim.getHeight());
				}
				final UImage logo = new UImage(PSystemVersion.getPlantumlImage());
				ug.apply(new UTranslate(lastX, y - logo.getHeight())).draw(logo);
			}
		};
	}

	public static List<TextBlock> getCols(List<String> lines, final int nbCol, final int reserved) throws IOException {
		final List<TextBlock> result = new ArrayList<TextBlock>();
		final int maxLine = (lines.size() + (nbCol - 1) + reserved) / nbCol;
		for (int i = 0; i < lines.size(); i += maxLine) {
			final List<String> current = lines.subList(i, Math.min(lines.size(), i + maxLine));
			result.add(GraphicStrings.createBlackOnWhite(current));
		}
		return result;
	}

	private List<String> getDonors() throws IOException {
		final List<String> lines = new ArrayList<String>();
		final Transcoder t = new TranscoderImpl(new AsciiEncoder(), new StringCompressorNone(),
				new CompressionBrotli());
		final String s = t.decode(DONORS).replace('*', '.');
		final StringTokenizer st = new StringTokenizer(s, BackSlash.NEWLINE);
		while (st.hasMoreTokens()) {
			lines.add(st.nextToken());
		}
		return lines;
	}

	public DiagramDescription getDescription() {
		return new DiagramDescription("(Donors)");
	}

	public static PSystemDonors create() {
		return new PSystemDonors();
	}

}
