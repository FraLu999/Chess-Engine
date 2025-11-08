/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author luc
 */
public class Board {
    
    /*
        8 | 56 57 58 59 60 61 62 63
        7 | 48 49 50 51 52 53 54 55
        6 | 40 41 42 43 44 45 46 47
        5 | 32 33 34 35 36 37 38 39
        4 | 24 25 26 27 28 29 30 31
        3 | 16 17 18 19 20 21 22 23
        2 | 8  9  10 11 12 13 14 15
        1 | 0  1  2  3  4  5  6  7
          -------------------------
            a  b  c  d  e  f  g  h    
    */
    
    private long[] pieceBB = new long[12]; 
    //Bitboards for every type of piece (pawn, knight, bishop, rook, queen, king) 
    //for each color (white,black)
    
    private long whitePieces; 
    //Bitboard to see where white pieces are located
    
    private long blackPieces; 
    //Bitboard to see where black pieces are located
    
    private boolean whiteToMove = true; 
    //who's turn it is (true: white)
    
    private boolean[] castleRight = new boolean[4]; 
    // Castle Rights are saved here: (0: 0-0-0 w; 1: 0-0 w; 2: 0-0-0 b; 3: 0-0 b)
    // 0-0 : short castle and 0-0-0 : long castle
    
    private int enpassentSquare;
    
    public static final int whitePawns=0, whiteKnights=1, whiteBishops=2, whiteRooks=3, 
                            whiteQueens=4, whiteKing=5,
                            blackPawns=6, blackKnights=7, blackBishops=8, blackRooks=9,
                            blackQueens=10, blackKing=11, size=12;
    //indexes for each piece bitboard
    
    private final int captured=1, twoStepsMovedPawn=2, enpassent=3, castled=4;
    
    private final int pawn = 0, knight=1, bishop=2, rook=3, queen=4, king =5;
    
    public Board(){
        initialize();
    }
    
    private void initialize(){
        pieceBB[whitePawns]   = 0x000000000000FF00L;
        pieceBB[whiteKnights] = (1L << 1) | (1L << 6);
        pieceBB[whiteBishops] = (1L << 2) | (1L << 5);
        pieceBB[whiteRooks]   = (1L << 0) | (1L << 7);
        pieceBB[whiteQueens]  = (1L << 3);
        pieceBB[whiteKing]    = (1L << 4);
        pieceBB[blackPawns]   = 0x00FF000000000000L;
        pieceBB[blackKnights] = (1L << 57) | (1L << 62);
        pieceBB[blackBishops] = (1L << 58) | (1L << 61);
        pieceBB[blackRooks]   = (1L << 56) | (1L << 63);
        pieceBB[blackQueens]  = (1L << 59);
        pieceBB[blackKing]    = (1L << 60);
    }
    
    public long occupied(){
        long allBB = 0L; //all bitboards combined
        for(long bb : pieceBB){
            allBB |= bb;
        }
        return allBB;
    }
    
    public long whitePiecesMask(){
        long whiteMask = 0L;
        for(int i = 0; i < 6; i++){
            whiteMask |= pieceBB[i];
        }
        return whiteMask;
    }
    
    public long blackPiecesMask(){
        long blackMask = 0L;
        for(int i = 6; i < 12; i++){
            blackMask |= pieceBB[i];
        }
        return blackMask;
    }
    
    public boolean isOccupied(int square){
        long mask = 1L << square;
        return (mask & occupied()) != 0;
    }
    
    public int getEnpassentSquare(){
        return enpassentSquare;
    }
    
    private int getFrom(int move){
        //From Square
        return move & 0x3F; 
    }
    
    private int getTo(int move){
        //End Square
        return (move >>> 6) & 0x3F; 
    }
    
    private int getFlag(int move){
        /*
        0: normal move ; 1: Capture ; 2: pawn moved 2 squares forward;
        3: en passent; 4: Castle; 
        */
        return (move >>> 12) & 0xF;
    }
    
    private int getMovedPiece(int move){
        /*
        0: pawn; 1: knight ; 2: bishop ; 3: Rook ; 4: Queen ; 5: King
        */
        return (move >>> 16) & 0xF;
    }
    
    private int getCapturedPiece(int move){
        /*
        0: pawn; 1: knight ; 2: bishop ; 3: Rook ; 4: Queen ; 5: no capture 
        */
        return (move >>> 20) & 0xF;
    }
    
    private int getPromotionPiece(int move){
        /*
        0: no promotion ; 1: knight ; 2: bishop ; 3: Rook ; 4: Queen
        */
        return (move >>> 24) & 0xF;
    }
    
    public void movePiece(int move){
        int from           = getFrom(move);
        int to             = getTo(move);
        int flag           = getFlag(move);
        int movedPiece     = getMovedPiece(move);
        int capturedPiece  = getCapturedPiece(move);
        int promotionPiece = getPromotionPiece(move);
        
        long fromMask = 1L << from;
        long toMask = 1L << to;
        
        pieceBB[movedPiece] &= ~fromMask;
        
        if (flag == castled || movedPiece == king){
            if(flag == castled || movedPiece == king){
                if(whiteToMove){
                    castleRight[0] = castleRight[1] = false;
                    if(flag == castled){
                        pieceBB[whiteKing] |= toMask;
                        if(to == 2){
                            pieceBB[whiteRooks] &= ~(1L << 0);
                            pieceBB[whiteRooks] |= (1L << 3);
                        }
                        else if(to == 6){
                            pieceBB[whiteRooks] &= ~(1L << 7);
                            pieceBB[whiteRooks] |= (1L << 5);
                        }
                    }
                }
                else{
                    castleRight[2] = castleRight[3] = false;
                    if(flag == castled){
                        pieceBB[blackKing] |= toMask;
                        if(to == 58){
                            pieceBB[blackRooks] &= ~(1L << 56);
                            pieceBB[blackRooks] |= (1L << 59);
                        }
                        else if(to == 62){
                            pieceBB[blackRooks] &= ~(1L << 63);
                            pieceBB[blackRooks] |= (1L << 61);
                        }
                    }
                }
            }
        }
        else if(movedPiece == rook){
            if(whiteToMove){
                if(from == 0) castleRight[0] = false;
                else if(from == 7) castleRight[1] = false;
            }
            else{
                if(from == 56) castleRight[2] = false;
                else if(from == 63) castleRight[3] = false;
            }
        }
        
        if(flag==twoStepsMovedPawn){ 
            enpassentSquare = (from+to)/2;
        }
        
        if(flag == enpassent){
            int capturedPawnSquare = whiteToMove ? to - 8 : to + 8;
            pieceBB[capturedPiece] &= ~(1L << capturedPawnSquare);
        }
        else if(capturedPiece != 5){
            pieceBB[capturedPiece] &= ~toMask;
        }
        
        if(flag != castled){
            if(promotionPiece==0) pieceBB[movedPiece] |= toMask;
            else                  pieceBB[promotionPiece] |= toMask;
        }
    }    
}
