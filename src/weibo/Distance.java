package weibo;
public class Distance {
	public int edit(int[] a12, int[] a22) {
		int[] a1 = new int[a12.length];
		int[] a2 = new int[a22.length];
		return edit(a1, a2);
	}

	public static int edit(Integer o1[], Integer o2[]) {
		int len1 = o1.length;
		int len2 = o2.length;

		// len1+1, len2+1, because finally return dp[len1][len2]
		int[][] dp = new int[len1 + 1][len2 + 1];

		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i;
		}

		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j;
		}

		// iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			int c1 = o1[i].intValue();
			for (int j = 0; j < len2; j++) {
				int c2 = o2[j].intValue();

				// if last two chars equal
				if (c1 == c2) {
					// update dp value for +1 length
					dp[i + 1][j + 1] = dp[i][j];
				} else {
					int replace = dp[i][j] + 1;
					int insert = dp[i][j + 1] + 1;
					int delete = dp[i + 1][j] + 1;

					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					dp[i + 1][j + 1] = min;
				}
			}
		}

		return dp[len1][len2];

	}

	public static int LCS(Integer a[], Integer b[]) {
		int[][] lengths = new int[a.length + 1][b.length + 1];

		// row 0 and column 0 are initialized to 0 already

		for (int i = 0; i < a.length; i++)
			for (int j = 0; j < b.length; j++)
				if (a[i] == b[j])
					lengths[i + 1][j + 1] = lengths[i][j] + 1;
				else
					lengths[i + 1][j + 1] = Math.max(lengths[i + 1][j],
							lengths[i][j + 1]);

		// read the substring out from the matrix
		StringBuffer sb = new StringBuffer();
		for (int x = a.length, y = b.length; x != 0 && y != 0;) {
			if (lengths[x][y] == lengths[x - 1][y])
				x--;
			else if (lengths[x][y] == lengths[x][y - 1])
				y--;
			else {
				sb.append(a[x - 1]);
				x--;
				y--;
			}
		}

		return sb.length();
	}
}
