 Song.songBuilder().title("You & Me").length(173).leadArtist(Artist.artistBuilder().name("Ryan Bluth").build()).genre("Dance").build()
 Song.songBuilder().title("Song 2").length(200).leadArtist(Artist.artistBuilder().name("Blur").build()).genre("90's").build()
 Song.songBuilder().title("Cash Out").length(150).leadArtist(Artist.artistBuilder().name("Calvin Harris").build()).genre("Pop").build()
 Song.songBuilder().title("Hello").length(100000).leadArtist(Artist.artistBuilder().name("Lionel Richie").build()).genre("Sad").build()


Recording.recordingBuilder()
                .title("Cash Out")
                .tracks(ImmutableSet.of( Song.songBuilder().title("Cash Out").length(150).leadArtist(Artist.artistBuilder().name("Calvin Harris").build()).genre("Pop").build()))
                .type(Recording.RecordType.SINGLE)
                .artist(Artist.artistBuilder().name("Calvin Harris").type("Solo").build())
                .build()

   Recording.recordingBuilder()
                   .title("Random")
                   .tracks(ImmutableSet.of( Song.songBuilder().title("Cash Out").length(150).leadArtist(Artist.artistBuilder().name("Calvin Harris").build()).genre("Pop").build(),
                       Song.songBuilder().title("Song 2").length(200).leadArtist(Artist.artistBuilder().name("Blur").build()).genre("90's").build(),
                       Song.songBuilder().title("You & Me").length(173).leadArtist(Artist.artistBuilder().name("Ryan Bluth").build()).genre("Dance").build()))
                   .type(RecordType.LP)
                   .build()

 Playlist.playlistBuilder()
                 .title("Pop tunes")
                 .tracks(ImmutableList.of(
                         Song.songBuilder().title("Cash Out").length(150).leadArtist(Artist.artistBuilder().name("Calvin Harris").build()).genre("Pop").build(),
                         Song.songBuilder().title("Hello").length(100000).leadArtist(Artist.artistBuilder().name("Lionel Richie").build()).genre("Sad").build(),
                         Song.songBuilder().title("You & Me").length(173).leadArtist(Artist.artistBuilder().name("Ryan Bluth").build()).genre("Dance").build(),
                         Song.songBuilder().title("Song 2").length(200).leadArtist(Artist.artistBuilder().name("Blur").build()).genre("90's").build()
                 ))
                 .build()

Artist.artistBuilder()
                .name("Calvin Harris")
                .type("Solo")
                .bio(Bio.bioBuilder()
                        .biography("Calvin is an excellent man, blah blah blah")
                        .build())
                .build());
               
Artist.artistBuilder()
                .name("Lionel Richie")
                .type("Solo")
                .bio(Bio.bioBuilder()
                        .biography("Famed for his ballads.... lalalalalalala")
                        .build())
                .build()