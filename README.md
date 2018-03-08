# OldMovieRemover

This setup works on all operating systems and will automatically delete old movies. Additionally you can set a whitelist file or generate this whitelist file from plex playlists.

## Automatically delete old movies

* Install java
* Download MovieCleanup.java from https://github.com/plankes-projects/OldMovieRemover/releases
* Create an empty file as your whitelist file.
* Run with
> java -jar MovieCleanup.java <whitelist_file> <clean_directory> <max_dirs> <del|safe>

eg.: 
> java -jar MovieCleanup.java /volume/whitelistfile /volume/movies/ 100 safe

This script will automatically delete files/directories if you have more than the defined number in your clean_directory path. It preferes files/directories which has the oldest 'created date'. 'Modified date' will be taken if your file system does not support 'created date'.

The safe flag in the end performs a safe run. This means it will not delete any data but instead only prints out what it would delete.
Use the del flag for deletion.

eg.: 
> java -jar MovieCleanup.java /volume/whitelistfile /volume/movies/ 100 del

## Create whitelist file manually
One movie per line.
One line should look like this:
/volume/movies/My Movie (2018)/my.movie.mkv

The parent directory, in this case 'My Movie (2018)', will be compared.

## Generate whitelist file from plex playlists

* Install python https://www.python.org/
* Install pip https://pip.pypa.io/en/stable/installing/
* Run with
> pip install plexapi
* Download https://github.com/plankes-projects/OldMovieRemover/blob/master/plex_playlist_extractor.py 
* Get plex token: https://support.plex.tv/articles/204059436-finding-an-authentication-token-x-plex-token/
* Run with
> python plex_playlist_extractor.py <plex_token> <plex_server> <target_file>

eg:
> python plex_playlist_extractor.py "asdeuz23SDacvsgh" "https://myplexserver.com:32400" /volume/whitelistfile

I recomment to run the whitelist_file generation shortly before the delete script.

## FAQ
### Why dont you perform this in one script?
I still want to cleanup my movies if plex connection fails.
