#!/bin/bash

CS_HOME="$(dirname $0)"

CS_SVG_FILE="$1"

if [[ ! -e $CS_SVG_FILE ]]; then
  echo "Usage: $0 [svg_file]"
  exit 1
fi

CS_SVG_FILE_DIR="$(dirname $CS_SVG_FILE)"
CS_SVG_FILE_BASE="$(basename $CS_SVG_FILE)"

CS_ELM_FILE_BASE="${CS_SVG_FILE_BASE%.*}.elm"
CS_ELM_FILE="$CS_SVG_FILE_DIR/$CS_ELM_FILE_BASE"

echo Converting $CS_SVG_FILE_BASE into $CS_ELM_FILE_BASE ...

java -cp "$CS_HOME"/target/cs-elm-svg-converter-1.0-*.jar com.csaltos.elm.svg.converter.CsElmSvgConverterApp < "$CS_SVG_FILE" > "$CS_ELM_FILE"

echo Ready !!
