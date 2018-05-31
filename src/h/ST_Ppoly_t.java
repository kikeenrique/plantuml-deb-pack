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
import smetana.core.UnsupportedArrayOfPtr;
import smetana.core.UnsupportedArrayOfStruct;
import smetana.core.UnsupportedStarStruct;
import smetana.core.UnsupportedStructAndPtr;
import smetana.core.__array_of_ptr__;
import smetana.core.__array_of_struct__;
import smetana.core.__ptr__;
import smetana.core.__struct__;
import smetana.core.amiga.Area;
import smetana.core.amiga.StarArrayOfPtr;
import smetana.core.amiga.StarArrayOfStruct;
import smetana.core.amiga.StarStruct;

public class ST_Ppoly_t extends UnsupportedStructAndPtr implements HardcodedStruct {

	// public List ps1;
	public StarArrayOfPtr ps2;
	public int pn;

	public ST_Ppoly_t() {
		this(null);
	}

	public ST_Ppoly_t(StarStruct parent) {
	}

	@Override
	public __struct__ copy() {
		ST_Ppoly_t result = new ST_Ppoly_t();
		result.ps2 = this.ps2;
		result.pn = this.pn;
		return result;
	}

	class Adaptor extends UnsupportedArrayOfPtr {

		final private StarArrayOfStruct newData2;
		final private int pos;

		public Adaptor(StarArrayOfStruct newData2, int pos) {
			this.newData2 = newData2;
			this.pos = pos;
		}

		@Override
		public __array_of_ptr__ move(int delta) {
			return new Adaptor(newData2, pos + delta);
		}

		@Override
		public Area getInternal(int idx) {
			return newData2.getInternalArray().getInternal(pos + idx);
		}

		@Override
		public __ptr__ getPtr() {
			return newData2.plus(pos).getPtr();
		}

		@Override
		public __struct__ getStruct() {
			return newData2.plus(pos).getStruct();
		}

		@Override
		public int comparePointerInternal(__array_of_ptr__ other) {
			return super.comparePointerInternal(other);
		}

	}

	class Adaptor2 extends UnsupportedArrayOfStruct {

		final private int pos;

		public Adaptor2(int pos) {
			this.pos = pos;
		}

		@Override
		public __array_of_struct__ plus(int delta) {
			return new Adaptor2(pos + delta);
		}
		
		@Override
		public __struct__ getStruct() {
			return ps2.plus(pos).getStruct();
		}

	}

	@Override
	public __ptr__ setPtr(String fieldName, __ptr__ newData) {
		if (fieldName.equals("ps")) {
			if (newData instanceof StarArrayOfStruct) {
				StarArrayOfStruct newData2 = (StarArrayOfStruct) newData;
				System.err.println("newData2B=" + newData2);
				this.ps2 = new StarArrayOfPtr(new Adaptor(newData2, 0));
				return ps2;
			}
			this.ps2 = (StarArrayOfPtr) newData;
			return ps2;
		}
		return super.setPtr(fieldName, newData);
	}

	@Override
	public __ptr__ getPtr(String fieldName) {
		if (fieldName.equals("ps")) {
			return ps2;
		}
		return super.getPtr(fieldName);
	}

	@Override
	public __array_of_struct__ getArrayOfStruct(String fieldName) {
		if (fieldName.equals("ps")) {
			return new Adaptor2(0);
			// return ps2.getArrayOfStruct("ps");
		}
		return super.getArrayOfStruct(fieldName);
	}

	@Override
	public __array_of_ptr__ getArrayOfPtr(String fieldName) {
		if (fieldName.equals("ps")) {
			return ps2.getInternalArray();
		}
		return super.getArrayOfPtr(fieldName);
	}

	@Override
	public void memcopyFrom(Area source) {
		ST_Ppoly_t source2 = (ST_Ppoly_t) source;
		this.ps2 = source2.ps2;
		this.pn = source2.pn;
	}

	@Override
	public void setInt(String fieldName, int data) {
		if (fieldName.equals("pn")) {
			this.pn = data;
			return;
		}
		super.setInt(fieldName, data);
	}

	@Override
	public int getInt(String fieldName) {
		if (fieldName.equals("pn")) {
			return pn;
		}
		return super.getInt(fieldName);
	}

	public class Amp extends UnsupportedStarStruct {

		@Override
		public int getInt(String fieldName) {
			if (fieldName.equals("pn")) {
				return pn;
			}
			return super.getInt(fieldName);
		}

		@Override
		public void setInt(String fieldName, int data) {
			if (fieldName.equals("pn")) {
				pn = data;
				return;
			}
			super.setInt(fieldName, data);
		}

		@Override
		public __ptr__ getPtr(String fieldName) {
			if (fieldName.equals("ps")) {
				return ps2;
			}
			return super.getPtr(fieldName);
		}

		@Override
		public __ptr__ setPtr(String fieldName, __ptr__ newData) {
			if (fieldName.equals("ps")) {
				ps2 = (StarArrayOfPtr) newData;
				return newData;
			}
			return super.setPtr(fieldName, newData);
		}
	}

	@Override
	public StarStruct amp() {
		return new Amp();
	}

	// public static List<String> DEFINITION = Arrays.asList(
	// "typedef struct Ppoly_t",
	// "{",
	// "Ppoint_t *ps",
	// "int pn",
	// "}",
	// "Ppoly_t");
}

// typedef struct Ppoly_t {
// Ppoint_t *ps;
// int pn;
// } Ppoly_t;