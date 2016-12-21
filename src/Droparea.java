/**
 * Created by eczn on 2016/12/4.
 */

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class Droparea extends Container implements DropTargetListener {
    private Jplayer jp;
    // 构造器
    public Droparea(Jplayer Jplayer ){
        jp = Jplayer;
        new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, this);
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
        System.out.println("dropActionChanged");
    }
    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
        System.out.println("dragEnter");
    }
    @Override
    public void dragExit(DropTargetEvent dte) {
        System.out.println("dragExit");
    }
    @Override
    public void dragOver(DropTargetDragEvent dtde) {
//        System.out.println("dragOver");
    }

    @Override // 最重要的事件： 文件拖入后释放在程序里
    public void drop(DropTargetDropEvent dtde) {
        System.out.println("drop");
        try {
            if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) { // 如果你拖进来的是文件
                dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);

                // 文件可能不是一个 用链表保存
                List list = (List) (
                        dtde.getTransferable()
                                .getTransferData(DataFlavor.javaFileListFlavor)
                );
                Iterator iterator = list.iterator();

                // 遍历这些文件
                while (iterator.hasNext()) {
                    File f = (File) iterator.next();
                    System.out.println("拖入的文件是："+f.getAbsolutePath());
                    jp.path = f.getAbsolutePath();
                    jp.livelist.addElem(f.getAbsolutePath());

                    // 如果这时候正在播放音频 则要先暂停释放掉这个mediaplayer 再播放
                    if (jp.mediaPlayer != null){
                        jp.mediaPlayer.stop();
                        jp.thePlay(10); // and play it;
                    } else { // 不然就直接播放
                        jp.thePlay(0);
                    }
                }

                jp.livelist.listSave();

                dtde.dropComplete(true);

            } else { // 如果是字符串
                dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                Object drop2here = dtde.getTransferable().getTransferData(DataFlavor.stringFlavor);
                // 将该字符串添加到livelist中
                jp.livelist.addElem(drop2here.toString());
                System.out.println(dtde.getTransferable().getTransferData(DataFlavor.stringFlavor));
            }
        } catch (IOException ioe) {  // 异常
            ioe.printStackTrace();
        } catch (UnsupportedFlavorException ufe) {
            ufe.printStackTrace();
        }
    }
}
