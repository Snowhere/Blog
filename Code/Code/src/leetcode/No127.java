package leetcode;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Stack;

/**
 * 给定两个单词（beginWord 和 endWord）和一个字典，找到从 beginWord 到 endWord 的最短转换序列的长度。转换需遵循如下规则：
 *
 * 每次转换只能改变一个字母。
 * 转换过程中的中间单词必须是字典中的单词。
 * 说明:
 *
 * 如果不存在这样的转换序列，返回 0。
 * 所有单词具有相同的长度。
 * 所有单词只由小写字母组成。
 * 字典中不存在重复的单词。
 * 你可以假设 beginWord 和 endWord 是非空的，且二者不相同。
 * 示例 1:
 *
 * 输入:
 * beginWord = "hit",
 * endWord = "cog",
 * wordList = ["hot","dot","dog","lot","log","cog"]
 *
 * 输出: 5
 *
 * 解释: 一个最短转换序列是 "hit" -> "hot" -> "dot" -> "dog" -> "cog",
 *      返回它的长度 5。
 * 示例 2:
 *
 * 输入:
 * beginWord = "hit"
 * endWord = "cog"
 * wordList = ["hot","dot","dog","lot","log"]
 *
 * 输出: 0
 *
 * 解释: endWord "cog" 不在字典中，所以无法进行转换。
 *
 */
public class No127 {

    //回溯
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Stack<String> stack=new Stack<>();
        length(beginWord,endWord,wordList,stack);
        int size = stack.size();
        return (size==1||size==0)?size:size+1;
    }

    public void length(String current, String target, List<String> wordList, Stack<String> stack) {
        stack.push(current);
        if (!oneLength(current, target)) {
            for (String word : wordList) {
                if (!stack.contains(word)&&oneLength(current, word)) {
                    length(word, target, wordList,stack);
                }
            }
            stack.pop();
        }
    }

    public boolean oneLength(String a, String b) {
        int diff=0;
        for (int i = 0; i < a.length(); i++) {
            if(a.charAt(i)!=b.charAt(i)){
                diff++;
            }
        }
        return diff==1;
    }

    public static void main(String[] args) {
        No127 no127 = new No127();
        System.out.println(no127.ladderLength("hit", "cog", Lists.newArrayList("hot", "dot", "dog", "lot", "log", "cog")));
    }
}
