import { Link } from "react-router-dom";

const RoustTest = () => {
  return (
    <div>
      <Link to={"/"}>Home</Link>
      <br />
      <Link to={"/Edit"}>Edit</Link>
      <br />
      <Link to={"/Push"}>Push</Link>
      <br />
    </div>
  );
};

export default RoustTest;
