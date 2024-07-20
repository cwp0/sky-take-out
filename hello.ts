// ts指定函数的参数类型为string
function hello(msg: string) {
    console.log(msg);
}
// 传入的参数类型威number，编译时会报错
hello(123)