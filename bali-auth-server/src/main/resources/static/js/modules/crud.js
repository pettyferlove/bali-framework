layui.define(function(exports){
    let crud = {
        hello: function(str){
            alert('Hello '+ (str||'mymod'));
        }
    };
    exports('crud', crud);
});