package org.gzy.queue.priorityQueue;

import org.gzy.queue.ILightQueue;

import java.util.Comparator;

/**
 * @author GaoZiYang
 * @since 2021年09月12日 22:54:43
 */
public class LightPriorityQueueTest {
    public static void main(String[] args) {
        // 分数越高的人优先级越高
        ILightQueue<Student> queue = new LightPriorityQueue<>(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getScore() - o2.getScore();
            }
        });
        queue.offer(new Student("张三", 75));
        queue.offer(new Student("李四", 82));
        queue.offer(new Student("王五", 59));
        queue.offer(new Student("李华", 99));
        queue.offer(new Student("刘键", 68));
        System.out.println(queue.poll());
    }

    private static class Student {
        /**
         * 姓名
         */
        private String name;
        /**
         * 分数
         */
        private int score;

        public Student(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", score=" + score +
                    '}';
        }
    }
}
