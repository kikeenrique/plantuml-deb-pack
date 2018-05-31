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

import smetana.core.HardcodedStruct;
import smetana.core.UnsupportedStarStruct;
import smetana.core.UnsupportedStructAndPtr;
import smetana.core.__ptr__;
import smetana.core.__struct__;
import smetana.core.amiga.StarStruct;

public class ST_Agobj_s extends UnsupportedStructAndPtr implements WithParent, HardcodedStruct {

	public final ST_Agtag_s tag = new ST_Agtag_s(this);
	public ST_Agrec_s.Amp data;

	@Override
	public void copyDataFrom(__struct__ other) {
		ST_Agobj_s this2 = (ST_Agobj_s) other;
		this.tag.copyDataFrom((__struct__) this2.tag);
		this.data = this2.data;

	}

	private final StarStruct parent;

	public ST_Agobj_s() {
		this(null);
	}

	public ST_Agobj_s(StarStruct parent) {
		this.parent = parent;
	}

	@Override
	public StarStruct amp() {
		return new Amp();
	}

	public class Amp extends UnsupportedStarStruct {

		@Override
		public __struct__ getStruct(String fieldName) {
			return ST_Agobj_s.this.getStruct(fieldName);
		}

		@Override
		public __ptr__ getPtr(String fieldName) {
			return ST_Agobj_s.this.getPtr(fieldName);
		}

		@Override
		public __ptr__ setPtr(String fieldName, __ptr__ newData) {
			return ST_Agobj_s.this.setPtr(fieldName, newData);
		}

		@Override
		public __ptr__ castTo(Class dest) {
			return ST_Agobj_s.this.castTo(dest);
		}
	}

	@Override
	public __ptr__ castTo(Class dest) {
		if (dest == Agobj_s.class) {
			return this;
		}
		if (dest == Agraph_s.class && parent instanceof ST_Agraph_s) {
			return (ST_Agraph_s) parent;
		}
		if (dest == Agnode_s.class && parent instanceof ST_Agnode_s) {
			return (ST_Agnode_s) parent;
		}
		if (dest == Agedge_s.class && parent instanceof ST_Agedge_s) {
			return (ST_Agedge_s) parent;
		}
		return super.castTo(dest);
	}

	@Override
	public __ptr__ setPtr(String fieldName, __ptr__ newData) {
		if (fieldName.equals("data")) {
			if (newData instanceof ST_Agnodeinfo_t) {
				ST_Agnodeinfo_t info = (ST_Agnodeinfo_t) newData;
				newData = (ST_Agrec_s) info.castTo(Agrec_s.class);
			} else if (newData instanceof ST_Agedgeinfo_t) {
				ST_Agedgeinfo_t info = (ST_Agedgeinfo_t) newData;
				newData = (ST_Agrec_s) info.castTo(Agrec_s.class);
			} else if (newData instanceof ST_Agedgeinfo_t.Amp) {
				ST_Agedgeinfo_t.Amp info = (ST_Agedgeinfo_t.Amp) newData;
				newData = (ST_Agrec_s) info.castTo(Agrec_s.class);
			}
			if (newData instanceof ST_Agrec_s) {
				this.data = (ST_Agrec_s.Amp) ((ST_Agrec_s) newData).amp();
			} else {
				this.data = (ST_Agrec_s.Amp) newData;
			}
			return data;
		}
		return super.setPtr(fieldName, newData);
	}

	@Override
	public __ptr__ getPtr(String fieldName) {
		if (fieldName.equals("data")) {
			return data;
		}
		return super.getPtr(fieldName);
	}

	@Override
	public __struct__ getStruct(String fieldName) {
		if (fieldName.equals("tag")) {
			return tag;
		}
		return super.getStruct(fieldName);
	}

	@Override
	public void setStruct(String fieldName, __struct__ newData) {
		if (fieldName.equals("tag")) {
			this.tag.copyDataFrom(newData);
			return;
		}
		super.setStruct(fieldName, newData);
	}

	public StarStruct getParent() {
		return parent;
	}

	// public interface ST_Agobj_s extends __ptr__ {
	// public static List<String> DEFINITION = Arrays.asList(
	// "struct Agobj_s",
	// "{",
	// "Agtag_t tag",
	// "Agrec_t *data",
	// "}");
}

// struct Agobj_s {
// Agtag_t tag;
// Agrec_t *data;
// };