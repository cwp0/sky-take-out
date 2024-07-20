/**
 * @File: TSDemo1.ts
 * @Description: 练习ts
 * @Author: cwp0
 * @CreatedTime: 2024/07/20 15:35
 * @Version: 1.0
 */
// 字符串类型
var username = 'cwp0';
// 数字类型
var age = 18;
// 布尔类型
var isMan = true;
console.log(username, age, isMan);
// 字面量类型，指定参数alignment只能是left right center三个值
function printText(s, alignment) {
    console.log(s, alignment);
}
printText('hello', 'left');
var person = {
    name: 'cwp0',
    age: 18
};
var person1 = {
    name: 'cwp0'
};
console.log(person);
console.log(person1);
// class类
var Animal = /** @class */ (function () {
    function Animal(name) {
        this.name = name;
    }
    Animal.prototype.run = function () {
        console.log(this.name + ' is running.');
    };
    return Animal;
}());
var dog = new Animal('dog');
dog.run();
