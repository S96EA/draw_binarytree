#!/bin/sh
dot out.gv | gvpr -c -ftree.gv | neato -n -Tpng -o binaryTree.png
display binaryTree.png
