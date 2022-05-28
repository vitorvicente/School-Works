import React from "react";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";

import Landing from "./components/Landing";
import Roles from "./components/Roles";
import File from "./components/File";

import {
  antiOne,
  antiThree,
  antiTwo,
  bibliography,
  gamebook,
  im,
  indFive,
  indFour,
  indOne,
  indThree,
  indTwo,
  proFour,
  proOne,
  proThree,
  proTwo
} from "./components/Files";


const App = () => {
  return (
    <Router basename={"XC410"}>
      <div>
        <Routes>
          <Route path={"/gamebook"} element={<File title={"Gamebook"} file={gamebook}/>}/>
          <Route path={"/im"} element={<File title={"Instructor's Manual"} file={im}/>}/>
          <Route path={"/bibliography"} element={<File title={"Bibliography"} file={bibliography}/>}/>
          <Route path={"/roles"} element={<Roles/>}/>

          <Route path={"/roles/anti-strike/one"} element={<File title={"Charles Strophel"} file={antiOne}/>}/>
          <Route path={"/roles/anti-strike/two"} element={<File title={"Albert D. Jones Jr."} file={antiTwo}/>}/>
          <Route path={"/roles/anti-strike/three"} element={<File title={"Thomas Liggett"} file={antiThree}/>}/>

          <Route path={"/roles/indeterminate/one"} element={<File title={"Louis M. Walsh"} file={indOne}/>}/>
          <Route path={"/roles/indeterminate/two"} element={<File title={"Roger Clark"} file={indTwo}/>}/>
          <Route path={"/roles/indeterminate/three"} element={<File title={"Robert O’Donnell"} file={indThree}/>}/>
          <Route path={"/roles/indeterminate/four"} element={<File title={"Joseph Moore"} file={indFour}/>}/>
          <Route path={"/roles/indeterminate/five"} element={<File title={"Bill Gardiner"} file={indFive}/>}/>

          <Route path={"/roles/pro-strike/one"} element={<File title={"William Foster"} file={proOne}/>}/>
          <Route path={"/roles/pro-strike/two"} element={<File title={"Mother Jones "} file={proTwo}/>}/>
          <Route path={"/roles/pro-strike/three"} element={<File title={"Walter Loan"} file={proThree}/>}/>
          <Route path={"/roles/pro-strike/four"} element={<File title={"Edward Johnson"} file={proFour}/>}/>

          <Route path={"*"} element={<Landing/>}/>
        </Routes>
      </div>
    </Router>
  );
}

export default App;
