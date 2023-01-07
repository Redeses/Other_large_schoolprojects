import React from 'react';
import './App.css';
import Menu from "./Components/Menu"
import {BrowserRouter as Router, Switch, Route} from "react-router-dom";


function App() {
  return (
      <Router>
        <Switch>
          <Route path="/" exact component={Menu}/>
        </Switch>
      </Router>
  );
}

export default App;
