bigNum :: IO()
bigNum = do {
putStr "Enter a big number: " ;
xs <- getLine;
if length(xs)<2 then bigNum else putStr "Your number is big enough \n";
}


triangle :: Char -> Int -> [String]
triangle x n = [replicate i x | i <- [1 .. n]]

printStars :: IO ()
printStars = do
  putStr $ unlines $ triangle '*' 5 