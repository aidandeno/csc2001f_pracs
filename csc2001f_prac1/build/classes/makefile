#Simple makefile for compiling classes for csc2001f_prac1
#DNBAID001 - Aidan de Nobrega

JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	BSTMain.java \
	SimpleBST.java \
	BinaryTreeNode.java \
        SimpleTreeWriter.java \
        SimpleTreeWriterImpl.java \
	TreeUtils.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
	$(RM) *.out

run: default
	java BSTMain
