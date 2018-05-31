/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * Project Info:  http://plantuml.com
 * 
 * If you like this project or if you find it useful, you can support us at:
 * 
 * http://plantuml.com/patreon (only 1$ per month!)
 * http://plantuml.com/paypal
 * 
 * This file is part of Smetana.
 * Smetana is a partial translation of Graphviz/Dot sources from C to Java.
 *
 * (C) Copyright 2009-2017, Arnaud Roques
 *
 * This translation is distributed under the same Licence as the original C program:
 * 
 *************************************************************************
 * Copyright (c) 2011 AT&T Intellectual Property 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: See CVS logs. Details at http://www.graphviz.org/
 *************************************************************************
 *
 * THE ACCOMPANYING PROGRAM IS PROVIDED UNDER THE TERMS OF THIS ECLIPSE PUBLIC
 * LICENSE ("AGREEMENT"). [Eclipse Public License - v 1.0]
 * 
 * ANY USE, REPRODUCTION OR DISTRIBUTION OF THE PROGRAM CONSTITUTES
 * RECIPIENT'S ACCEPTANCE OF THIS AGREEMENT.
 * 
 * You may obtain a copy of the License at
 * 
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package h;

import smetana.core.UnsupportedSize_t;
import smetana.core.UnsupportedStarStruct;
import smetana.core.UnsupportedStructAndPtr;
import smetana.core.__struct__;
import smetana.core.size_t;
import smetana.core.amiga.StarArrayOfPtr;
import smetana.core.amiga.StarStruct;

public class ST_Pedge_t extends UnsupportedStructAndPtr {

	private final StarStruct parent;

	public ST_Pedge_t() {
		this(null);
	}

	public ST_Pedge_t(StarStruct parent) {
		this.parent = parent;
	}

	// "typedef struct Pedge_t",
	// "{",
	// "Ppoint_t a, b",
	private final ST_pointf a = new ST_pointf(this);
	private final ST_pointf b = new ST_pointf(this);

	// "}",
	// "Pedge_t");

	@Override
	public StarStruct amp() {
		return new Amp();
	}

	public class Amp extends UnsupportedStarStruct {

	}
	
	@Override
	public __struct__ getStruct(String fieldName) {
		if (fieldName.equals("a")) {
			return a;
		}
		if (fieldName.equals("b")) {
			return b;
		}
		return super.getStruct(fieldName);
	}
	
	@Override
	public void setStruct(String fieldName, __struct__ newData) {
		if (fieldName.equals("a")) {
			this.a.copyDataFrom(newData);
			return;
		}
		if (fieldName.equals("b")) {
			this.b.copyDataFrom(newData);
			return;
		}
		super.setStruct(fieldName, newData);
	}

	public static size_t sizeof(final int nb) {
		return new UnsupportedSize_t(nb) {
			@Override
			public Object malloc() {
				return new StarArrayOfPtr(new STArray<ST_Pedge_t>(nb, 0, ST_Pedge_t.class));
			}

			@Override
			public int getInternalNb() {
				return nb;
			}

			@Override
			public Object realloc(Object old) {
				StarArrayOfPtr old2 = (StarArrayOfPtr) old;
				old2.realloc(nb);
				return old2;
			}
		};
	}

}

// typedef struct Pedge_t {
// Ppoint_t a, b;
// } Pedge_t;