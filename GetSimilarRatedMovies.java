
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

//m - number of movies , n - recommended movies

/*
Time Complexity - O(m*log n)
Explanation: Visiting each movie once (O(m)) using DFS method.
Additionally, maintaining the top n rated movies in heap.
So time complexity for heap is O(log n) for each insert operation.
So the overall time complexity is O(m*log n)
*/

/*
Space Complexity - O(m+n) if m >>> n then O(m)
Explanation: Using set to maintain the list of visited movies - O(n).
Using the heap to store the recommended movies - O(m).
*/

/*
Note: We can also solve the problem using Tree Set with a space complexity of O(m) but time
is always O(m log m). As we don't need to sort all the m movies for this requirement I opted
for Heap and Set data structures.
*/

public class GetSimilarRatedMovies {

	static List<Movie> getMovieRecommendations(Movie movie, int countOfMoviesToRecommend) throws Exception {
		if(movie==null || countOfMoviesToRecommend<=0 || movie.similarMovies.isEmpty()) {
			return new ArrayList<Movie>();
		}
		Set<Movie> visitedMovies = new HashSet<Movie>();
		visitedMovies.add(movie);
		Queue<Movie> topRatedMoviesList = new PriorityQueue<Movie>();
		getMovieRecommendationsHelper(movie, visitedMovies, topRatedMoviesList, countOfMoviesToRecommend);
		return new ArrayList<Movie>(topRatedMoviesList);
	}

	private static void getMovieRecommendationsHelper(Movie movie, Set<Movie> visitedMovies,
									Queue<Movie> topRatedMoviesList, int countOfMoviesToRecommend) {

		for (Movie similarMovie: movie.similarMovies) {

			if(!visitedMovies.contains(similarMovie)) {
				visitedMovies.add(similarMovie);
				if(topRatedMoviesList.size() == countOfMoviesToRecommend) {
					if(topRatedMoviesList.peek().rating < similarMovie.rating) {
						topRatedMoviesList.poll();
						topRatedMoviesList.offer(similarMovie);
					}
				} else {
					topRatedMoviesList.add(similarMovie);
				}
				getMovieRecommendationsHelper(similarMovie, visitedMovies, topRatedMoviesList, countOfMoviesToRecommend);
			}

		}
	}


	public static void main(String[] args) {
		Movie A = new Movie("A",1.2);
		Movie B = new Movie("B",3.6);
		Movie C = new Movie("C",2.4);
		Movie D = new Movie("D",4.8);
		Movie E = new Movie("E",5.0);
		A.addSimilarMovie(B);
		B.addSimilarMovie(A);
		A.addSimilarMovie(C);
		C.addSimilarMovie(A);
		B.addSimilarMovie(D);
		D.addSimilarMovie(B);
		E.addSimilarMovie(D);
		D.addSimilarMovie(E);
		try {
			System.out.println(getMovieRecommendations(A,1));
			System.out.println(getMovieRecommendations(A,2));
			System.out.println(getMovieRecommendations(A,4));
			System.out.println(getMovieRecommendations(C,1));
			System.out.println(getMovieRecommendations(E,1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

	}
}

class Movie implements Comparable<Movie> {
	String movieName;
	double rating;
	Set<Movie> similarMovies;

	Movie(String movieName, double rating) {
		this.movieName = movieName;
		this.rating = rating;
		this.similarMovies = new HashSet<Movie>();
	}

	void addSimilarMovie(Movie similarMovie) {
		similarMovies.add(similarMovie);
	}

	@Override
	public String toString() {
		return "(" + movieName + ","+rating+")";
	}

	@Override
	public int compareTo(Movie o) {
		if(this.rating>o.rating) {
			return 1;
		}
		else if(this.rating<o.rating) {
			return -1;
		}

		return 0;
	}


}
