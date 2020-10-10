package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) {


        ListNode ls3 = new ListNode(4, null);
        ListNode ls2 = new ListNode(2, ls3);
        ListNode l = new ListNode(1, ls2);

        ListNode ls5 = new ListNode(3, null);
        ListNode r = new ListNode(1, ls5);


        ListNode start = printList(l, r);

        while(start != null) {
            System.out.println(start.val);
            start = start.next;
        }
    }

    public static ListNode printList(ListNode l, ListNode r) {
        ListNode seq = new ListNode();
        ListNode beg = seq;
        while (l != null && r != null) {
            if(l.val >= r.val ) {
                seq.next = new ListNode(l.val);
                l = l.next;
            } else if(r.val > l.val) {
                seq.next = new ListNode(r.val);
                r = r.next;
            }
            if(l == null) {
                while(r !=null) {
                    seq.next = new ListNode(r.val);
                    r = r.next;
                }
            }

            if(r == null) {
                while(l !=null) {
                    seq.next = new ListNode(l.val);
                    l = l.next;
                }
            }
        }

        return beg.next;
    }

}