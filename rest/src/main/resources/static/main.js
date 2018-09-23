let app = new Vue({
    el: '#app',
    data: {
        message: 'Hello Vuee!',
        gameState: false,
    },
});


function game(id) {
    app.$data.gameState = gameState;
    /*
    jQuery.get('http://localhost:8080/game?id=' + id, function(result) {
        app.$data = result;
        }
    );
    */

}

game('1bc6354a-46aa-4e59-8237-47c6cd0d78c6');
