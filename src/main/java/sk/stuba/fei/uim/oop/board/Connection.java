package sk.stuba.fei.uim.oop.board;

import sk.stuba.fei.uim.oop.board.tile.Tile;

public class Connection {
    private Tile connectedTile;
    private boolean connected;

    public Connection(){
        this.connectedTile = null;
        this.connected = false;
    }

    public void makeConnection(Tile tile) {
        this.connectedTile = tile;
        this.connected = true;
    }

}
