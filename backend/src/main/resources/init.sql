start transaction;
create fulltext index artist_name_idx on artist (name) with parser ngram;
create fulltext index artwork_name_idx on artwork (name) with parser ngram;
create fulltext index genre_name_idx on genre (name) with parser ngram;
commit;