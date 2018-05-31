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
package net.sourceforge.plantuml.command;

import net.sourceforge.plantuml.AbstractPSystem;
import net.sourceforge.plantuml.CharSequence2;
import net.sourceforge.plantuml.ErrorUml;
import net.sourceforge.plantuml.ErrorUmlType;
import net.sourceforge.plantuml.PSystemError;
import net.sourceforge.plantuml.core.Diagram;
import net.sourceforge.plantuml.core.DiagramType;
import net.sourceforge.plantuml.core.UmlSource;
import net.sourceforge.plantuml.utils.StartUtils;
import net.sourceforge.plantuml.version.IteratorCounter2;

public abstract class PSystemBasicFactory<P extends AbstractPSystem> extends PSystemAbstractFactory {

	public PSystemBasicFactory(DiagramType diagramType) {
		super(diagramType);
	}

	public PSystemBasicFactory() {
		this(DiagramType.UML);
	}

	public abstract P executeLine(P system, String line);

	public P init(String startLine) {
		return null;
	}

	private boolean isEmptyLine(CharSequence2 result) {
		return result.trin().length() == 0;
	}

	final public Diagram createSystem(UmlSource source) {
		final IteratorCounter2 it = source.iterator2();
		final CharSequence2 startLine = it.next();
		P system = init(startLine.toString2());
		boolean first = true;
		while (it.hasNext()) {
			final CharSequence2 s = it.next();
			if (first && s != null && isEmptyLine(s)) {
				continue;
			}
			first = false;
			if (StartUtils.isArobaseEndDiagram(s)) {
				if (source.getTotalLineCount() == 2 && source.isStartDef() == false) {
					return buildEmptyError(source, s.getLocation());
				}
				if (system != null) {
					system.setSource(source);
				}
				return system;
			}
			system = executeLine(system, s.toString2());
			if (system == null) {
				final ErrorUml err = new ErrorUml(ErrorUmlType.SYNTAX_ERROR, "Syntax Error?",
				/* it.currentNum() - 1, */s.getLocation());
				return new PSystemError(source, err, null);
			}
		}
		if (system != null) {
			system.setSource(source);
		}
		return system;
	}

}
