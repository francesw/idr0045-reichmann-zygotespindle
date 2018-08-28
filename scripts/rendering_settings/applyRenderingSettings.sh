#!/bin/bash

omero="/opt/omero/server/OMERO.server/bin/omero"

cd ../../experimentA/rendering_settings

for f in `find *.json`
do
	ds=${f%.*}

	echo "Set rendering settings for $ds"
	
	dsid=`$omero hql --style csv -q "select d.id from Dataset d where d.name = '$ds'"`
	dsid=${dsid##*,}
	
	$omero render set Dataset:$dsid $f
done
