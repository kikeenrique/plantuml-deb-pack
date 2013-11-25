#!/bin/sh
# PlantUML Launcher
#
# This script is free software; you can redistribute it and/or modify it
# under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This script distributed in the hope that it will be useful, but
# WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
# or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
# License for more details.
# You should have received a copy of the GNU General Public
# License along with this library; if not, write to the Free Software
# Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
# USA.
#
# Copyright (C) 2010 Ilya Paramonov <ivparamonov@gmail.com>

if [ -n "${JAVA_HOME}" ] && [ -x "${JAVA_HOME}/bin/java" ] ; then
    JAVA="${JAVA_HOME}/bin/java"
elif [ -x /usr/bin/java ] ; then
    JAVA=/usr/bin/java
else
    echo Cannot find JVM
    exit 1
fi

$JAVA -jar /usr/share/plantuml/plantuml.jar ${@}
