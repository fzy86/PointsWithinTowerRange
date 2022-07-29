import java.util.*;

public class PointsWithinTowerRange {
    public static void main(String[] args) {
        Point pointA = new Point(2, 2);
        Point pointB = new Point(3,3);

        Tower tower1 = new Tower(2,2,3);
        Tower tower2 = new Tower(3,3,2);
        Tower tower3 = new Tower(4,4,2);
        Tower tower4 = new Tower(7,7,1);
        Tower tower5 = new Tower(8,8,3);

        List<Tower> towerList = new ArrayList<>();
        towerList.add(tower1);
        towerList.add(tower2);
        towerList.add(tower3);
        towerList.add(tower4);
        towerList.add(tower5);

        System.out.println(canReachPointBWithinRange(pointA, pointB, towerList));
    }

    private static boolean canReachPointBWithinRange(Point pointA, Point pointB, List<Tower> towerList) {
        HashMap<Tower, List<Tower>> map = new HashMap<>();
        HashSet<Tower> visited = new HashSet<>();
        for ( Tower tower : towerList ) {
            map.putIfAbsent(tower, new LinkedList<>());
        }

        for ( int i = 0; i < towerList.size(); i++ ) {
            for ( int j = i + 1; j < towerList.size(); j++ ) {
                Tower temp1 = towerList.get(i);
                Tower temp2 = towerList.get(j);
                if ( towerOverlap(temp1, temp2) )
                    map.get(temp1).add(temp2);
            }
        }

        for ( Tower tower : towerList ) {
            if ( pointInTowerRange(pointA, tower)) {
                for ( Tower tempTower : map.get(tower)) {
                    if ( dfs(pointB, tempTower, visited,map))
                        return true;
                }

            }
        }

        return false;
    }

    private static boolean dfs(Point pointB, Tower tower, HashSet<Tower> visited, HashMap<Tower, List<Tower>> map) {
        if (visited.contains(tower))
            return false;
        if (pointInTowerRange(pointB, tower))
            return true;
        visited.add(tower);
        for ( Tower tempTower : map.get(tower) ) {
            if ( dfs(pointB, tempTower, visited, map) )
                return true;
        }
        return false;
    }

    private static boolean pointInTowerRange(Point point, Tower tower) {
        return Math.hypot(tower.x - point.x, tower.y - point.y) <= tower.range;
    }

    private static boolean towerOverlap(Tower tower1, Tower tower2) {
        return Math.hypot(tower1.x - tower2.x, tower1.y - tower2.y) < tower1.range + tower2.range;
    }
}
