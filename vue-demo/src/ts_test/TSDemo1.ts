/**
 * @File: TSDemo1.ts
 * @Description: 练习ts
 * @Author: cwp0
 * @CreatedTime: 2024/07/20 15:35
 * @Version: 1.0
 */

// 字符串类型
let username: string = 'cwp0';
// 数字类型
let age: number = 18;
// 布尔类型
let isMan: boolean = true;

console.log(username, age, isMan);

// 字面量类型，指定参数alignment只能是left right center三个值
function printText(s: string, alignment: 'left' | 'right' | 'center'): void {
  console.log(s, alignment);
}

printText('hello', 'left');

// interface类型
interface Person {
  name: string;
  age?: number; // 可选属性

}

const person: Person = {
    name: 'cwp0',
    age: 18
}
const person1: Person = {
    name: 'cwp0'
}

console.log(person);
console.log(person1);

// class类
class Animal {
  name: string; // 类中的属性
  constructor(name: string) {
    this.name = name;
  }
  run() {
    console.log(this.name + ' is running.');
  }
}

const dog = new Animal('dog');
dog.run();

// 实现接口
class Student implements Person {
  name: string;
  age?: number;
  constructor(name: string, age?: number) {
    this.name = name;
    this.age = age;
  }
}

// 继承
class Cat extends Animal {
  run() {
    console.log(this.name + ' is running fast.');
  }
}


