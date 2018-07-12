from plexapi.server import PlexServer
import json
import sys
import os
import codecs

if len(sys.argv) != 4:
    sys.exit('Usage: ' + os.path.basename(__file__) + ' <plex_token> <target_file>')

plex_token = sys.argv[1]
server = sys.argv[2]
file_path = sys.argv[3]

plex = PlexServer(server, plex_token)

movies = []
for playlist in plex.playlists():
    if playlist.playlistType != "audio":
        for item in playlist.items():
            for loc in item.locations:
                movies.append(loc)

with codecs.open(file_path, "w", "utf-8") as text_file:
  for movie in movies:
    text_file.write("%s\n" % movie)
