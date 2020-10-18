package model;

public class Film {

    private int filmID;
    private String filmTitle;
    private String filmGenre;
    private int filmYear;
    private int filmRating;

    public Film(int filmID, String filmTitle, String filmGenre, int filmYear, int filmRating) {
        this.filmID = filmID;
        this.filmTitle = filmTitle;
        this.filmGenre = filmGenre;
        this.filmYear = filmYear;
        this.filmRating = filmRating;
    }

    public int getFilmID() {
        return filmID;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public String getFilmGenre() {
        return filmGenre;
    }

    public int getFilmYear() {
        return filmYear;
    }

    public int getFilmRating() {
        return filmRating;
    }

}
