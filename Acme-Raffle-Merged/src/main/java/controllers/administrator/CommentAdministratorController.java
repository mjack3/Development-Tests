
package controllers.administrator;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Comment;
import domain.TabooWord;
import services.CommentService;
import services.TabooWordService;

@RequestMapping("/comment/administrator")
@Controller
public class CommentAdministratorController extends AbstractController {

	public CommentAdministratorController() {
		super();
	}


	@Autowired
	private CommentService		commentService;
	@Autowired
	private TabooWordService	tabooWordService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView resul = new ModelAndView("comment/list");

		List<Comment> comments = this.commentService.findAll();
		comments = this.hiddenTabooWords(comments);
		resul.addObject("comments", comments);
		resul.addObject("requestURI", "comment/administrator/list.do");
		return resul;
	}

	@RequestMapping(value = "/listbad", method = RequestMethod.GET)
	public ModelAndView listBad() {
		final ModelAndView resul = new ModelAndView("comment/list");

		List<Comment> comments = (List<Comment>) this.commentService.findAllInapropiate();
		comments = this.hiddenTabooWords(comments);
		resul.addObject("comments", comments);
		return resul;
	}

	private List<Comment> hiddenTabooWords(final List<Comment> comments) {
		final List<Comment> res = new LinkedList<>();

		for (final TabooWord t : this.tabooWordService.findAll())
			for (final Comment c : comments) {
				if (c.getText().contains(t.getName())) {
					String text = "";
					final String[] words = c.getText().split(" ");
					String chars = "";
					for (int i = 0; i < t.getName().length(); i++)
						chars = chars + "*";

					for (final String w : words)
						if (w.equalsIgnoreCase(t.getName()))
							text += chars + " ";
						else
							text += w + " ";
					c.setText(text);
				}
				if (!res.contains(c))
					res.add(c);
			}

		return res;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int commentId) {

		ModelAndView resul;

		try {

			this.commentService.delete(commentId);
			resul = this.list();

		} catch (final Throwable oops) {

			resul = this.list();
			resul.addObject("message", "comment.commit.error");
		}

		return resul;
	}

}
