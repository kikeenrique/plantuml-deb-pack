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
 * Revision $Revision: 3837 $
 *
 */
package net.sourceforge.plantuml.salt;

import net.sourceforge.plantuml.StringUtils;
import net.sourceforge.plantuml.command.PSystemBasicFactory;
import net.sourceforge.plantuml.core.DiagramType;

public class PSystemSaltFactory extends PSystemBasicFactory<PSystemSalt> {

	public PSystemSaltFactory(DiagramType diagramType) {
		super(diagramType);
	}

	public PSystemSalt init(String startLine) {
		if (getDiagramType() == DiagramType.UML) {
			return null;
		} else if (getDiagramType() == DiagramType.SALT) {
			return new PSystemSalt();
		} else {
			throw new IllegalStateException(getDiagramType().name());
		}

	}

	@Override
	public PSystemSalt executeLine(PSystemSalt system, String line) {
		if (system == null && line.equals("salt")) {
			return new PSystemSalt();
		}
		if (system == null) {
			return null;
		}
		system.add(StringUtils.trin(line));
		return system;
	}

}
