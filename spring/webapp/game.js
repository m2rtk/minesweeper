'use strict';


function Cell(rowIndex, colIndex, state) {
    var visible_state = get_visible_state(state);
    return React.createElement(
        'button',
        {
            onMouseDown: event => {
                post_and_render(
                    event.button == 0 ? 'open' : 'flag', 
                    rowIndex, 
                    colIndex
                );
            },
            onContextMenu: (event) => { 
                event.preventDefault()
            },
            key: rowIndex + "-" + colIndex,
            className: 'cell',
            style: {
                color: get_cell_color(visible_state)
            }
        },
        visible_state
    );
}

function get_visible_state(state) {
    if (state.open) {
        if (state.bomb) {
            return 'X';
        }
        if (state.nearbyBombs === 0) {
            return '-';
        }
        return state.nearbyBombs;
    }

    if (state.flagged) {
        return 'F';
    }

    return '';
}

function get_cell_color(visible_state) {
    if (Number.isInteger(visible_state)) {
        return [null, 'green', 'darkcyan', 'blue', 'darkgreen', 'cyan', 'darkblue', 'pink', 'yellow'][visible_state];
    }

    if (['F', 'X'].includes(visible_state)) {
        return 'red';
    }

    return 'black';
}


function Row(rowIndex, cells) {
    return React.createElement(
        'div', 
        {
            key: rowIndex,
            className: 'board-row'
        }, 
        cells.map((state, colIndex) => Cell(rowIndex, colIndex, state))
    );
}


function Board(rows) {
    return React.createElement(
        'div',
        {
            className: 'board'
        },
        rows.map((cells, rowIndex) => Row(rowIndex, cells))
    );
}


function post(endpoint, data) {
    return axios({
        method: 'post',
        url: API + endpoint,
        params: data
    }).catch(error => {
        console.log(error);
    });
}

function post_and_render(action, row, col) {
    post(action, {id:GAME_ID, row:row, col:col}).then(response => {
        handle_game_state(response.data);
    });
}

function new_game() {
    return post('newgame', {height: 16, width: 16, bombs: 40}).then(response => {
        GAME_ID = response.data.id;
        handle_game_state(response.data.game);
    });
}

function handle_game_state(game) {
    render_grid(game.grid);
    render_state(game.state);
}

function render_grid(grid) {
    ReactDOM.render(
        Board(grid.cells),
        document.querySelector('#game')
    );
}

function render_state(state) {
    ReactDOM.render(
        React.createElement(
            'h3',
            null,
            state
        ),
        document.querySelector('#state')
    )
}

var API = 'minesweeper/api/';
var GAME_ID = null;
new_game();