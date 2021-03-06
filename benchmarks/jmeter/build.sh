#!/bin/sh

JAVA_HOME=/nfs/spectre/a4/solaris8/common/j2sdk1.4.2
export JAVA_HOME
PATH=/nfs/spectre/a4/solaris8/common/j2sdk1.4.2/bin:${PATH}
export PATH

#--------------------------------------------
# No need to edit anything past here
#--------------------------------------------
if test -z "$JAVA_HOME" ; then
    echo "ERROR: JAVA_HOME not found in your environment."
    echo "Please, set the JAVA_HOME variable in your environment to match the"
    echo "location of the Java Virtual Machine you want to use."
    exit
fi

if test -f $JAVA_HOME/lib/tools.jar ; then
    CLASSPATH=$CLASSPATH:${JAVA_HOME}/lib/tools.jar
fi

# convert the existing path to unix
if [ "$OSTYPE" = "cygwin32" ] || [ "$OSTYPE" = "cygwin" ] ; then
   CLASSPATH=`cygpath --path --unix "$CLASSPATH"`
fi

for i in ./lib/*.jar
do
    if [ i != "./lib/jorphan.jar" ]
	then
		CLASSPATH=$CLASSPATH:$i
	fi
done

for i in ./lib/javamail-1.3.1/lib/*.jar
do
    CLASSPATH=$CLASSPATH:$i
done

for i in ../jakarta-site2/lib/*.jar
do
    CLASSPATH=$CLASSPATH:$i
done

# convert the unix path to windows
if [ "$OSTYPE" = "cygwin32" ] || [ "$OSTYPE" = "cygwin" ] ; then
   CLASSPATH=`cygpath --path --windows "$CLASSPATH"`
fi

BUILDFILE=build.xml
echo $JAVA_HOME/bin/java -classpath $CLASSPATH org.apache.tools.ant.Main -buildfile $BUILDFILE "$@"

$JAVA_HOME/bin/java -classpath $CLASSPATH org.apache.tools.ant.Main \
                      -buildfile $BUILDFILE "$@"
